package io.lele.supermarket.pricer.application.port.in.dto.product;

import io.lele.supermarket.pricer.adapter.out.jpa.mapper.UnitOfMeasurementMapper;
import io.lele.supermarket.pricer.application.utils.StringHelper;
import io.lele.supermarket.pricer.core.ModelValidate;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PhysicalQuantity;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
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

    public CreateProductDTO(String name, BigDecimal unitPrice, BigDecimal pricedQuantity, PricingType pricingType, String unitOfMeasurement, PhysicalQuantity physicalQuantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.pricedQuantity = pricedQuantity;
        this.pricingType = pricingType;
        this.unitOfMeasurement = unitOfMeasurement;
        this.physicalQuantity = physicalQuantity;
    }

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
                    UnitOfMeasurement unit = UnitOfMeasurementMapper.toUnitOfMeasurement(unitOfMeasurement);
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
