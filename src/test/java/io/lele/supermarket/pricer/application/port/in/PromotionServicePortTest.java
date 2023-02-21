package io.lele.supermarket.pricer.application.port.in;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.CreatePromotionDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.UpdatePromotionDTO;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PromotionEligibilityBase;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromotionServicePortTest {

    @Autowired
    private PromotionRequestPort productServicePort;

    @Test
    void createPromotionServicePort() {
        CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);

        Promotion domain = productServicePort.create(model);
        assertNotNull(domain, "Promotion object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Boolean exists = productServicePort.recordExists(domain.getReference());
        assertTrue(exists, "Promotion record should be persisted");
    }

    @Test
    void updatePromotionServicePort() {
        CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);

        Promotion domain = productServicePort.create(model);
        assertNotNull(domain, "Promotion object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        UpdatePromotionDTO updateDTO  = new UpdatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(25), PriceReductionType.Percentage, domain.getReference());
        Promotion updateResponse  = productServicePort.update(updateDTO);

        assertNotNull(updateResponse, "Update Promotion data object should not be null");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(25)).doubleValue(), updateResponse.getMinimumPurchase().doubleValue(), "Update response Promotion minimum purchase should be 25");
        Optional<Promotion> updated = productServicePort.findById(updateResponse.getReference());
        assertTrue(updated.isPresent(), "Promotion should be persisted");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(25)).doubleValue(), updated.get().getMinimumPurchase().doubleValue(), "Persisted Promotion update minimum purchase should be 25");
    }


}