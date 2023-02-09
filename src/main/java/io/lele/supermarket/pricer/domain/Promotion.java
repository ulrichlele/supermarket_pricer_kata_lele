package io.lele.supermarket.pricer.domain;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import io.lele.supermarket.pricer.domain.enums.DiscountCriteriaBase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Promotion implements Serializable {

    private String reference = UUID.randomUUID().toString();
    private DiscountCriteriaBase evaluationType;
    //To simplify usage, units of measurement is equal to product units
    //private UnitOfMeasurement unitOfMeasurement;
    private BigDecimal minimumPurchase;
    private PromotionOfferType promotionOfferType;
    private BigDecimal offer;

    private PriceReductionType priceReductionType;


    public Promotion() {
    }

    public Promotion(DiscountCriteriaBase evaluationType, BigDecimal minimumPurchase, PromotionOfferType promotionOfferType, BigDecimal offer) {
        this.evaluationType = evaluationType;
        this.minimumPurchase = minimumPurchase;
        this.promotionOfferType = promotionOfferType;
        this.offer = offer;
    }

    public Promotion(DiscountCriteriaBase evaluationType, BigDecimal minimumPurchase, PromotionOfferType promotionOfferType, BigDecimal offer, PriceReductionType priceReductionType) {
        this.evaluationType = evaluationType;
        this.minimumPurchase = minimumPurchase;
        this.promotionOfferType = promotionOfferType;
        this.offer = offer;
        this.priceReductionType = priceReductionType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public DiscountCriteriaBase getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(DiscountCriteriaBase evaluationType) {
        this.evaluationType = evaluationType;
    }

    public BigDecimal getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(BigDecimal minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

    public PromotionOfferType getPromotionOfferType() {
        return promotionOfferType;
    }

    public void setPromotionOfferType(PromotionOfferType promotionOfferType) {
        this.promotionOfferType = promotionOfferType;
    }

    public PriceReductionType getPriceReductionType() {
        return priceReductionType;
    }

    public void setPriceReductionType(PriceReductionType priceReductionType) {
        this.priceReductionType = priceReductionType;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return getReference().equals(promotion.getReference());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getReference());
    }
}
