package io.lele.supermarket.pricer.domain;

import io.lele.supermarket.pricer.domain.enums.PricingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
@Data
@NoArgsConstructor
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
                ", reference='" + reference + '\'' +
                ", unitPrice=" + unitPrice +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }


}
