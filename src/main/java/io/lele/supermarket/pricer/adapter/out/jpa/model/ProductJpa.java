package io.lele.supermarket.pricer.adapter.out.jpa.model;

import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductJpa implements Serializable {


    @Column(name = "P_NAME")
    private String name;
    @Id
    @Column(name = "P_REF")
    private String reference;
    @Column(name = "P_UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "P_CCY")
    private String currencyCode;
    @Column(name = "P_PRICED_QTY")
    private BigDecimal pricedQuantity;
    @Column(name = "P_PRICE_TYPE")
    private PricingType pricingType;

    @Column(name = "P_PHYSICAL_QTY")
    private PhysicalQuantity physicalQuantity;
    @Column(name = "P_UNIT_MEASUREMENT")
    private String unitOfMeasurement;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROMOTION_ID", foreignKey = @ForeignKey(name = "FK_PRODUCT_PROMO"))
    private PromotionJpa promotion;

    public ProductJpa(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public ProductJpa(String name, BigDecimal unitPrice, BigDecimal pricedQuantity, PricingType pricingType, String unitOfMeasurement) {
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
        ProductJpa that = (ProductJpa) o;
        return Objects.equals(getReference(), that.getReference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReference());
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

