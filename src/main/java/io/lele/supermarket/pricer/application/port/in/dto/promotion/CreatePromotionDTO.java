package io.lele.supermarket.pricer.application.port.in.dto.promotion;

import io.lele.supermarket.pricer.core.ModelValidate;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.enums.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionDTO extends ModelValidate implements Serializable {

    @NotNull
    private PromotionEligibilityBase evaluationType;
    @NotNull
    @Min(0)
    private BigDecimal minimumPurchase;
    @NotNull
    private PromotionOfferType offerType;
    @Min(0)
    @NotNull
    private BigDecimal offer;

    private PriceReductionType priceReductionType;
    @Override
    protected Set<ValidatorResult> doCustomValidation() {
        Set<ValidatorResult> violations = getEmptyValidator();

        if (offerType != null && this.getOfferType().equals(PromotionOfferType.PriceReduction)) {
            if (this.getPriceReductionType() == null) {
                violations.add(new ValidatorResult("PriceReductionType.Null", this.getClass(), "priceReductionType") );
            } else if (this.getPriceReductionType().equals(PriceReductionType.Percentage) && this.getOffer().compareTo(new BigDecimal(100)) > 0) {
                violations.add(new ValidatorResult("Price discount percentage greater than 100", this.getClass(), "priceReductionType") );
            }
        }
        return violations;
    }
}
