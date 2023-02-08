package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.model.Promotion;
import io.lele.supermarket.pricer.model.enums.PriceReductionType;
import io.lele.supermarket.pricer.model.enums.PromotionOfferType;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterService;
import io.lele.supermarket.pricer.utils.AmountUtil;

import java.math.BigDecimal;

public class PromotionService {

    private UnitConverter converter = new DefaultUnitConverterService();

    void evaluateProductPromotion(BasketItem item) throws IncompatibleUnitsException, InvalidProductPromotion {
        if (item == null || item.getProduct() == null || item.getProduct().getPromotion() == null)
            return;
        Promotion promo = item.getProduct().getPromotion();
        validatePromotion(item.getProduct(), promo);
        boolean itemIsEligible = isBasketItemEligibleToPromotion(item, promo);
        if (itemIsEligible) {
            switch (promo.getPromotionOfferType()) {
                case Quantity:
                    switch (item.getProduct().getPricingType()) {
                        case PricePerItem:
                        case PriceOnQuantity:
                            item.setOfferedQuantity(promo.getOffer());
                            item.setTotalQuantity(item.getQuantity().add(promo.getOffer()));
                            break;
                        case PricePerUnitOfMeasurement:
                            BigDecimal offeredQty = converter.convert(promo.getOffer(), item.getProduct().getUnitOfMeasurement(), item.getUnitOfMeasurement());
                            item.setOfferedQuantity(offeredQty);
                            item.setTotalQuantity(item.getQuantity().add(offeredQty));
                            break;
                    }
                    break;
                case PriceReduction:
                    switch (promo.getPriceReductionType()) {
                        case Percentage:
                            BigDecimal percentagedPrice = (new BigDecimal(1).subtract(promo.getOffer().divide(new BigDecimal(100)))).multiply(item.getPrice());
                            item.setTotalPrice(AmountUtil.scaleAmount(percentagedPrice));
                            break;
                        case Flat:
                            BigDecimal reducedPrice = item.getPrice().subtract(promo.getOffer());
                            reducedPrice = reducedPrice.compareTo(BigDecimal.ZERO) >= 0 ? reducedPrice : BigDecimal.ZERO;
                            item.setTotalPrice(AmountUtil.scaleAmount(reducedPrice));
                            break;
                    }
                    break;
            }
        }

    }

    private void validatePromotion(Product product, Promotion promo) throws IncompatibleUnitsException, InvalidProductPromotion {
        if (promo.getEvaluationType() == null) {
            throw new InvalidProductPromotion("Promotion evaluation type null");
        }
        if (promo.getOffer() == null) {
            throw new InvalidProductPromotion("Offered value is null");
        }
        if (promo.getOffer().compareTo(new BigDecimal(0)) < 0) {
            throw new InvalidProductPromotion("Offered value is negative");
        }
        if (promo.getPromotionOfferType() == null) {
            throw new InvalidProductPromotion("Offer type is null");
        } else if (promo.getPromotionOfferType().equals(PromotionOfferType.PriceReduction)) {
            if (promo.getPriceReductionType() == null) {
                throw new InvalidProductPromotion("Price reduction type is null");
            } else if (promo.getPriceReductionType().equals(PriceReductionType.Percentage) && promo.getOffer().compareTo(new BigDecimal(100)) > 0) {
                throw new InvalidProductPromotion("Price discount percentage greater than 100");
            }
        }
        if (promo.getMinimumPurchase() != null && promo.getMinimumPurchase().compareTo(new BigDecimal(0)) < 0) {
            throw new InvalidProductPromotion("Minimum purchase is negative");
        }
    }

    boolean isBasketItemEligibleToPromotion(BasketItem item, Promotion promo) throws IncompatibleUnitsException {
        boolean isEligible = false;
        switch (promo.getEvaluationType()) {
            case None:
                isEligible = true;
                break;
            case PurchaseAmount:
                isEligible = item.getPrice().compareTo(promo.getMinimumPurchase()) >= 0;
                break;
            case PurchaseQuantity:
                switch (item.getProduct().getPricingType()) {
                    case PricePerItem:
                    case PriceOnQuantity:
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
