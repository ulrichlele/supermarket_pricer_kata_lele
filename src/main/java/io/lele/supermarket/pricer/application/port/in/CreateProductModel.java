package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductModel implements Serializable {

    @NotNull
    @NotEmpty
    private String name;

    @Min(0)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    private BigDecimal pricedQuantity = BigDecimal.ONE;

    @NotNull
    private PricingType pricingType = PricingType.PricePerItem;

    private UnitOfMeasurement unitOfMeasurement;
}
