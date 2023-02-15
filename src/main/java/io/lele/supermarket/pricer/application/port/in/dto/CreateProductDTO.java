package io.lele.supermarket.pricer.application.port.in.dto;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.utils.StringHelper;
import io.lele.supermarket.pricer.core.ModelValidate;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO extends ModelValidate implements Serializable {

    @NotNull
    @NotEmpty
    protected String name;

    @Min(0)
    protected BigDecimal unitPrice = BigDecimal.ZERO;

    protected BigDecimal pricedQuantity = BigDecimal.ONE;

    @NotNull
    protected PricingType pricingType = PricingType.PricePerItem;

    protected String unitOfMeasurement;

    protected PhysicalQuantity physicalQuantity;

    @Override
    protected Set<ValidatorResult> doCustomValidation() {
        Set<ValidatorResult> violations = getEmptyValidator();
        if(pricingType != null && pricingType.equals(PricingType.PricePerUnitOfMeasurement) && StringHelper.isEmpty(unitOfMeasurement)){
            violations.add(new ValidatorResult("PhysicalQuantity.Null", this.getClass(), "physicalQuantity") );
        }
        if(StringHelper.isNotEmpty(unitOfMeasurement)){
            if(physicalQuantity == null){
                violations.add(new ValidatorResult("PhysicalQuantity.Null", this.getClass(), "physicalQuantity") );
            }else{
                try{
                    UnitOfMeasurement unit = UnitOfMeasurementMapper.toUnitOfMeasurement(unitOfMeasurement, physicalQuantity);
                }catch (IllegalArgumentException ex){
                    violations.add(new ValidatorResult("Invalid.UnitOfMeasurement", this.getClass(), "unitOfMeasurement") );
                }
            }
        } else if(pricingType != null && pricingType.equals(PricingType.PricePerUnitOfMeasurement)){
            violations.add(new ValidatorResult("UnitOfMeasurement.Null", this.getClass(), "unitOfMeasurement") );
        }
        return violations;
    }
}
