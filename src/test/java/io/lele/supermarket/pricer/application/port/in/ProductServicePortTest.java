package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServicePortTest {

    @Autowired
    private ProductRequestPort productServicePort;

    @Test
    void createProductServicePort() {
        CreateProductDTO model = new CreateProductDTO("Table", new BigDecimal(5), null, PricingType.PricePerItem, null, null);

        Product domain = productServicePort.create(model);
        assertNotNull(domain, "Product object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Boolean exists = productServicePort.recordExists(domain.getReference());
        assertTrue(exists, "Product record should be persisted");
    }

    @Test
    void updateProductServicePort() {
        CreateProductDTO model = new CreateProductDTO("Table", new BigDecimal(10), null, PricingType.PricePerItem, null, null);

        Product domain = productServicePort.create(model);
        assertNotNull(domain, "Product object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        UpdateProductDTO updateDTO  = new UpdateProductDTO("Table price updated", new BigDecimal(14), null, PricingType.PricePerItem, null, null, domain.getReference());
        Product updateResponse  = productServicePort.update(updateDTO);

        assertNotNull(updateResponse, "Update Product data object should not be null");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(14.0)), updateResponse.getUnitPrice(), "Update response Product Unit price should be 14");
        Optional<Product> updated = productServicePort.findById(updateResponse.getReference());
        assertTrue(updated.isPresent(), "Product should be persisted");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(14)), updated.get().getUnitPrice(), "Persisted Product update Unit price should be 14");
    }


}