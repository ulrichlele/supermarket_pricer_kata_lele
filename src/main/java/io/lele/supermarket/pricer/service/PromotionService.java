package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.model.Promotion;
import io.lele.supermarket.pricer.service.impl.DefaultUnitConverterService;

import java.math.BigDecimal;

public class PromotionService {

    private UnitConverter converter = new DefaultUnitConverterService();

    void evaluateProductPromotion(BasketItem item) throws IncompatibleUnitsException, InvalidProductPromotion {
        if( item == null || item.getProduct() == null || item.getProduct().getPromotion() == null)
            return;
        Promotion promo = item.getProduct().getPromotion();
        validatePromotion(item.getProduct(), promo);
        boolean itemIsEligible = isBasketItemEligibleToPromotion(item, promo);
        if(itemIsEligible){
             switch (promo.getPromotionOfferType()){
                 case Quantity:
                     item.setOfferedQuantity(promo.getOffer());
                     item.setTotalQuantity(item.getQuantity().add(promo.getOffer()));
                     break;
             }
        }

    }

    private void validatePromotion(Product product, Promotion promo)throws IncompatibleUnitsException, InvalidProductPromotion{
        if(promo.getEvaluationType() == null){
            throw new InvalidProductPromotion("Promotion type null");
        }

        if(promo.getOffer() == null){
            throw new InvalidProductPromotion("Promotion offered is null");
        }
        if(promo.getOffer().compareTo(new BigDecimal(0)) < 0 ){
            throw new InvalidProductPromotion("Promotion offered is negative");
        }
        if(promo.getPromotionOfferType() == null){
            throw new InvalidProductPromotion("Promotion offer type is null");
        }
        if(promo.getMinimumPurchase() != null && promo.getMinimumPurchase().compareTo(new BigDecimal(0)) <0){
            throw new InvalidProductPromotion("Minimum purchase is negative");
        }



    }

    boolean isBasketItemEligibleToPromotion(BasketItem item, Promotion promo)throws IncompatibleUnitsException{
        boolean isEligible = false;
        switch (promo.getEvaluationType()){
            case Any:
                isEligible = true;
                break;
            case PurchasedPrice:
                isEligible = item.getPrice().compareTo(promo.getMinimumPurchase()) >=0;
                break;
            case PurchasedQuantity:
                switch (item.getProduct().getPricingType()){
                    case PricePerItem:
                    case PriceOnQuantity:
                        isEligible = promo.getMinimumPurchase() == null || promo.getMinimumPurchase().equals(BigDecimal.ZERO) || item.getQuantity().compareTo(promo.getMinimumPurchase()) >=0;
                        break;
                    case PricePerUnitOfMeasurement:
                        BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                        isEligible = convertedQty.compareTo(promo.getMinimumPurchase()) >=0;
                        break;
                }
        }
        return isEligible;
    }
}
