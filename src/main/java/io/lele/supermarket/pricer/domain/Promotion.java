package io.lele.supermarket.pricer.domain;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import io.lele.supermarket.pricer.domain.enums.PromotionEligibilityBase;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Promotion {

    private String reference = UUID.randomUUID().toString();
    private PromotionEligibilityBase evaluationType;
    /**
     * minimum purchase:
     * minimum quantity or minimum amount
     */
    private BigDecimal minimumPurchase;
    private PromotionOfferType offerType;
    private BigDecimal offer;
    private PriceReductionType priceReductionType;


    public Promotion() {
    }

    public Promotion(PromotionEligibilityBase evaluationType, BigDecimal minimumPurchase, PromotionOfferType promotionOfferType, BigDecimal offer) {
        this.evaluationType = evaluationType;
        this.minimumPurchase = minimumPurchase;
        this.offerType = promotionOfferType;
        this.offer = offer;
        validatePromotion();
    }

    public Promotion(PromotionEligibilityBase evaluationType, BigDecimal minimumPurchase, PromotionOfferType promotionOfferType, BigDecimal offer, PriceReductionType priceReductionType) {
        this.evaluationType = evaluationType;
        this.minimumPurchase = minimumPurchase;
        this.offerType = promotionOfferType;
        this.offer = offer;
        this.priceReductionType = priceReductionType;
        validatePromotion();
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public PromotionEligibilityBase getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(PromotionEligibilityBase evaluationType) {
        this.evaluationType = evaluationType;
    }

    public BigDecimal getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(BigDecimal minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

    public PromotionOfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(PromotionOfferType offerType) {
        this.offerType = offerType;
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

    private void validatePromotion() throws IncompatibleUnitsException, InvalidProductPromotion {
        if (this.getEvaluationType() == null) {
            throw new InvalidProductPromotion("Promotion evaluation type null");
        }
        if (this.getOffer() == null) {
            throw new InvalidProductPromotion("Offered value is null");
        }
        if (this.getOffer().compareTo(new BigDecimal(0)) < 0) {
            throw new InvalidProductPromotion("Offered value is negative");
        }
        if (this.getOfferType() == null) {
            throw new InvalidProductPromotion("Offer type is null");
        } else if (this.getOfferType().equals(PromotionOfferType.PriceReduction)) {
            if (this.getPriceReductionType() == null) {
                throw new InvalidProductPromotion("Price reduction type is null");
            } else if (this.getPriceReductionType().equals(PriceReductionType.Percentage) && this.getOffer().compareTo(new BigDecimal(100)) > 0) {
                throw new InvalidProductPromotion("Price discount percentage greater than 100");
            }
        }
        if (this.getMinimumPurchase() != null && this.getMinimumPurchase().compareTo(new BigDecimal(0)) < 0) {
            throw new InvalidProductPromotion("Minimum purchase is negative");
        }
    }
}
