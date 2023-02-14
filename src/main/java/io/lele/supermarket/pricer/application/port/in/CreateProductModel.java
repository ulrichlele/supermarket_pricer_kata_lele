package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.utils.StringHelper;
import io.lele.supermarket.pricer.core.ModelValidate;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateProductModel extends ModelValidate implements Serializable {

    @NotNull
    @NotEmpty
    private String name;

    @Min(0)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    private BigDecimal pricedQuantity = BigDecimal.ONE;

    @NotNull
    private PricingType pricingType = PricingType.PricePerItem;

    private String unitOfMeasurement;

    private PhysicalQuantity physicalQuantity;

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
