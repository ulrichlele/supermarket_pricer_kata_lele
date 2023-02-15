package io.lele.supermarket.pricer.application.port.in.dto;

import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO extends CreateProductDTO {
    @NotNull
    @NotEmpty
    private String reference;

    public UpdateProductDTO(@NotNull @NotEmpty String name, @Min(0) BigDecimal unitPrice, BigDecimal pricedQuantity, @NotNull PricingType pricingType, String unitOfMeasurement, PhysicalQuantity physicalQuantity, String reference) {
        super(name, unitPrice, pricedQuantity, pricingType, unitOfMeasurement, physicalQuantity);
        this.reference = reference;
    }
}
