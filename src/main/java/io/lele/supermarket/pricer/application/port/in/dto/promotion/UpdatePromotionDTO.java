package io.lele.supermarket.pricer.application.port.in.dto.promotion;

import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionEligibilityBase;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionDTO extends CreatePromotionDTO {
    @NotNull
    @NotEmpty
    private String reference;

    public UpdatePromotionDTO(@NotNull PromotionEligibilityBase evaluationType, @NotNull @Min(0) BigDecimal minimumPurchase, @NotNull PromotionOfferType offerType, @Min(0) @NotNull BigDecimal offer, PriceReductionType priceReductionType, String reference) {
        super(evaluationType, minimumPurchase, offerType, offer, priceReductionType);
        this.reference = reference;
    }

}
