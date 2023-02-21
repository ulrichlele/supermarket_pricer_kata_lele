package io.lele.supermarket.pricer.application.port.in.dto.product;

import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UpdateProductDTO extends CreateProductDTO {
    @NotNull
    @NotEmpty
    private String reference;

    public UpdateProductDTO(@NotNull @NotEmpty String name, @Min(0) BigDecimal unitPrice, BigDecimal pricedQuantity, @NotNull PricingType pricingType, String unitOfMeasurement, PhysicalQuantity physicalQuantity, String reference) {
        super(name, unitPrice, pricedQuantity, pricingType, unitOfMeasurement, physicalQuantity);
        this.reference = reference;
    }
}
