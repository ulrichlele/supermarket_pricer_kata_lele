package io.lele.supermarket.pricer.model;

import io.lele.supermarket.pricer.enums.PhysicalQuantity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BasketItem implements Serializable {

    Basket basket;

    Product product;
    BigDecimal quantity =  BigDecimal.ZERO;
    PhysicalQuantity physicalQuantity;
    UnitOfMeasurement unitOfMeasurement;

    BigDecimal price =  BigDecimal.ZERO;

    public BasketItem() {
    }

    public BasketItem(Basket basket, Product product) {
        this.basket = basket;
        this.product = product;
    }

    public BasketItem(Basket basket, Product product, BigDecimal quantity) {
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
    }

    public BasketItem(Basket basket, Product product, BigDecimal quantity, PhysicalQuantity physicalQuantity, UnitOfMeasurement unitOfMeasurement) {
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
        this.physicalQuantity = physicalQuantity;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public PhysicalQuantity getPhysicalQuantity() {
        return physicalQuantity;
    }

    public void setPhysicalQuantity(PhysicalQuantity physicalQuantity) {
        this.physicalQuantity = physicalQuantity;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItem that = (BasketItem) o;
        return Objects.equals(basket, that.basket) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(basket, getProduct());
    }
}
