package io.lele.supermarket.pricer.model;

import io.lele.supermarket.pricer.enums.EPricingType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

public class Product implements Serializable {

    private String name;
    private String refrence = UUID.randomUUID().toString();
    private BigDecimal unitPrice = BigDecimal.ZERO;

    private String currencyCode = "USD";

    private BigDecimal pricedQuantity = BigDecimal.ONE;

    private EPricingType pricingType = EPricingType.PricePerItem;


    public Product(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
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

    public EPricingType getPricingType() {
        return pricingType;
    }

    public void setPricingType(EPricingType pricingType) {
        this.pricingType = pricingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return refrence.equals(product.refrence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refrence);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", refrence='" + refrence + '\'' +
                ", unitPrice=" + unitPrice +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}