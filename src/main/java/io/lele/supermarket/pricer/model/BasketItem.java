package io.lele.supermarket.pricer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BasketItem implements Serializable {
    Product product;
    BigDecimal quantity =  BigDecimal.ZERO;

    BigDecimal price =  BigDecimal.ZERO;


    public BasketItem() {
    }

    public BasketItem(Product product) {
        this.product = product;
    }

    public BasketItem(Product product, BigDecimal quantity) {
        this.product = product;
        this.quantity = quantity;
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
        return getProduct().equals(that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct());
    }
}
