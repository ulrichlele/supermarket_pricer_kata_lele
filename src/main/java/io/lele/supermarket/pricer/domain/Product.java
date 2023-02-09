package io.lele.supermarket.pricer.domain;

import io.lele.supermarket.pricer.domain.enums.PricingType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product implements Serializable {

    private String name;
    private String reference = UUID.randomUUID().toString();
    private BigDecimal unitPrice = BigDecimal.ZERO;

    private String currencyCode = "USD";

    private BigDecimal pricedQuantity = BigDecimal.ONE;

    private PricingType pricingType = PricingType.PricePerItem;

    private UnitOfMeasurement unitOfMeasurement;

    private Promotion promotion;

    public Product(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Product(String name, BigDecimal unitPrice, BigDecimal pricedQuantity, PricingType pricingType, UnitOfMeasurement unitOfMeasurement) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.pricedQuantity = pricedQuantity;
        this.pricingType = pricingType;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getPricedQuantity() {
        return pricedQuantity;
    }

    public void setPricedQuantity(BigDecimal pricedQuantity) {
        this.pricedQuantity = pricedQuantity;
    }

    public PricingType getPricingType() {
        return pricingType;
    }

    public void setPricingType(PricingType pricingType) {
        this.pricingType = pricingType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return reference.equals(product.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", refrence='" + reference + '\'' +
                ", unitPrice=" + unitPrice +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}