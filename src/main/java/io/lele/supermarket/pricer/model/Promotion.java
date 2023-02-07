package io.lele.supermarket.pricer.model;

import io.lele.supermarket.pricer.model.enums.PromotionBase;
import io.lele.supermarket.pricer.model.enums.PromotionEvaluationType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Promotion implements Serializable {

    private String reference = UUID.randomUUID().toString();
    private PromotionEvaluationType type;
    private UnitOfMeasurement unitOfMeasurement;
    private BigDecimal minimumPurchase;
    private PromotionBase promotionBase;
    private BigDecimal offer;


    public Promotion() {
    }

    public Promotion(PromotionEvaluationType type, UnitOfMeasurement unitOfMeasurement, BigDecimal minimumPurchase, PromotionBase promotionBase, BigDecimal offer) {
        this.type = type;
        this.unitOfMeasurement = unitOfMeasurement;
        this.minimumPurchase = minimumPurchase;
        this.promotionBase = promotionBase;
        this.offer = offer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public PromotionEvaluationType getType() {
        return type;
    }

    public void setType(PromotionEvaluationType type) {
        this.type = type;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public BigDecimal getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(BigDecimal minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

    public PromotionBase getPromotionBase() {
        return promotionBase;
    }

    public void setPromotionBase(PromotionBase promotionBase) {
        this.promotionBase = promotionBase;
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
