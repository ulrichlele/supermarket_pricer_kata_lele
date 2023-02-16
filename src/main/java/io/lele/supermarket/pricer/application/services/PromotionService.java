package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class PromotionService {

    private UnitConverter converter;

    public PromotionService(UnitConverter unitConverter){
        this.converter = unitConverter;
    }

    public void evaluateProductPromotion(BasketItem item) throws IncompatibleUnitsException, InvalidProductPromotion {
        if (item == null || item.getProduct() == null || item.getProduct().getPromotion() == null)
            return;
        Promotion promo = item.getProduct().getPromotion();
        if(promo == null)
            return;
        boolean isItemEligible = isBasketItemEligibleToPromotion(item, promo);
        if (!isItemEligible) return;
        switch (promo.getPromotionOfferType()) {
            case Quantity: applyPromotionQuantity(item, promo.getOffer()); break;
            case PriceReduction: applyPromotionPriceReduction(item, promo.getOffer(), promo.getPriceReductionType()); break;
        }
    }

    private void applyPromotionPriceReduction(BasketItem item, BigDecimal offer, PriceReductionType type) {
        switch (type) {
            case Percentage:
                BigDecimal percentagedPrice = (new BigDecimal(1).subtract(offer.divide(new BigDecimal(100)))).multiply(item.getPrice());
                item.setTotalPrice(AmountUtil.scaleAmount(percentagedPrice));
                break;
            case Flat:
                BigDecimal reducedPrice = item.getPrice().subtract(offer);
                reducedPrice = reducedPrice.compareTo(BigDecimal.ZERO) >= 0 ? reducedPrice : BigDecimal.ZERO;
                item.setTotalPrice(AmountUtil.scaleAmount(reducedPrice));
                break;
        }
    }
    private void applyPromotionQuantity(BasketItem item, BigDecimal offer) throws IncompatibleUnitsException {
        switch (item.getProduct().getPricingType()) {
            case PricePerItem:
                item.setOfferedQuantity(offer);
                item.setTotalQuantity(item.getQuantity().add(offer));
                break;
            case PricePerUnitOfMeasurement:
                BigDecimal offeredQty = converter.convert(offer, item.getProduct().getUnitOfMeasurement(), item.getUnitOfMeasurement());
                item.setOfferedQuantity(offeredQty);
                item.setTotalQuantity(item.getQuantity().add(offeredQty));
                break;
        }
    }



    boolean isBasketItemEligibleToPromotion(BasketItem item, Promotion promo) throws IncompatibleUnitsException {
        boolean isEligible = false;
        switch (promo.getEvaluationType()) {
            case Always:
                isEligible = true;
                break;
            case PurchaseAmount:
                isEligible = item.getPrice().compareTo(promo.getMinimumPurchase()) >= 0;
                break;
            case PurchaseQuantity:
                switch (item.getProduct().getPricingType()) {
                    case PricePerItem:
                        isEligible = promo.getMinimumPurchase() == null || promo.getMinimumPurchase().equals(BigDecimal.ZERO) || item.getQuantity().compareTo(promo.getMinimumPurchase()) >= 0;
                        break;
                    case PricePerUnitOfMeasurement:
                        BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                        isEligible = convertedQty.compareTo(promo.getMinimumPurchase()) >= 0;
                        break;
                }
        }
        return isEligible;
    }
}
