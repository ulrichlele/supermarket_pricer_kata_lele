package io.lele.supermarket.pricer.model;

import io.lele.supermarket.pricer.model.enums.PromotionOfferType;
import io.lele.supermarket.pricer.model.enums.PromotionEvaluationType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Promotion implements Serializable {

    private String reference = UUID.randomUUID().toString();
    private PromotionEvaluationType evaluationType;
    //To simplify usage, units of measurement is equal to product units
    //private UnitOfMeasurement unitOfMeasurement;
    private BigDecimal minimumPurchase;
    private PromotionOfferType promotionOfferType;
    private BigDecimal offer;


    public Promotion() {
    }

    public Promotion(PromotionEvaluationType evaluationType, BigDecimal minimumPurchase, PromotionOfferType promotionOfferType, BigDecimal offer) {
        this.evaluationType = evaluationType;
        this.minimumPurchase = minimumPurchase;
        this.promotionOfferType = promotionOfferType;
        this.offer = offer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public PromotionEvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(PromotionEvaluationType evaluationType) {
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
