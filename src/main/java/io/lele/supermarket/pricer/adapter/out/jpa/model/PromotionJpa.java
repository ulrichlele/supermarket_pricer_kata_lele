package io.lele.supermarket.pricer.adapter.out.jpa.model;

import io.lele.supermarket.pricer.domain.enums.PromotionEligibilityBase;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PROMOTION")
public class PromotionJpa  implements Serializable {

    @Id
    @Column(name = "PR_REFERENCE")
    private String reference;

    @Column(name = "PR_DISCNT_CRIT_BASE")
    private PromotionEligibilityBase evaluationType;

    @Column(name = "PR_MIN_PURCHASE")
    private BigDecimal minimumPurchase;

    @Column(name = "PR_OFFER_TYPE")
    private PromotionOfferType promotionOfferType;

    @Column(name = "PR_OFFER")
    private BigDecimal offer;

    @Column(name = "PR_PRICE_REDUC_TYPE")
    private PriceReductionType priceReductionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionJpa that = (PromotionJpa) o;
        return Objects.equals(getReference(), that.getReference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReference());
    }
}
