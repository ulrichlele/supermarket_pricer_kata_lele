package io.lele.supermarket.pricer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Basket implements Serializable {

    private String refrence = UUID.randomUUID().toString();
    private List<BasketItem> items = new ArrayList<>(0);

    BigDecimal totalPrice = BigDecimal.ZERO;

    public Basket() {
    }

    public Basket(List<BasketItem> items) {
        this.items = items;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(getRefrence(), basket.getRefrence());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRefrence());
    }

    @Override
    public String toString() {
        return "Basket{" +
                "refrence='" + refrence + '\'' +
                ", items=" + items +
                '}';
    }
}
