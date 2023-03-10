package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.domain.BasketItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class BasketItemService {

    private UnitConverter converter;

    public UnitConverter getConverter() {
        return converter;
    }

    public BasketItemService(UnitConverter unitConverter) {
        this.converter = unitConverter;
    }

    public void evaluatePrice(BasketItem item) throws IncompatibleUnitsException {
        if (item.getProduct() != null && item.getProduct().getPricingType() != null) {
            BigDecimal price = BigDecimal.ZERO;
            if (item.getProduct().getUnitPrice().compareTo(BigDecimal.ZERO) < 0)
                throw new RuntimeException("Negative product unit price");
            switch (item.getProduct().getPricingType()) {
                case PricePerItem:
                    BigDecimal pricedQty = item.getProduct().getPricedQuantity();
                    pricedQty = pricedQty != null && pricedQty.compareTo(BigDecimal.ZERO) > 0 ? pricedQty : BigDecimal.ONE;
                    price = item.getProduct().getUnitPrice().divide(pricedQty, MathContext.DECIMAL128).multiply(item.getQuantity());
                    break;
                case PricePerUnitOfMeasurement:
                    BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                    price = convertedQty.multiply(item.getProduct().getUnitPrice());
                    break;
            }
            item.setPrice(AmountUtil.scaleAmount(price));
            item.setTotalQuantity(item.getQuantity());
            item.setTotalPrice(item.getPrice());
        }
    }


}
