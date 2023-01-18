package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.model.Product;

import java.math.BigDecimal;

public interface ProductPricer {
    BigDecimal evaluatePrice(Product product, int quantity);
}
