package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.dto.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.UpdateProductDTO;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import io.lele.supermarket.pricer.core.BaseResponse;
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
    private ProductServicePort productServicePort;


    @Test
    void createProductServicePort() {
        CreateProductDTO model = new CreateProductDTO("Table", new BigDecimal(5), null, PricingType.PricePerItem, null, null);

        BaseResponse<Product> response = productServicePort.create(model);
        assertNotNull(response, "Create product response object");
        assertTrue(response.isSuccess(), "Success status should be true");
        assertNotNull(response.getData(), "Product data object should not be null");
        assertNotNull(response.getData().getReference(), "Reference should not be null");

        Boolean exists = productServicePort.recordExists(response.getData().getReference());
        assertTrue(exists, "Product record should be persisted");
    }

    @Test
    void updateProductServicePort() {
        CreateProductDTO model = new CreateProductDTO("Table", new BigDecimal(10), null, PricingType.PricePerItem, null, null);

        BaseResponse<Product> response = productServicePort.create(model);
        assertNotNull(response, "Create product response object");
        assertTrue(response.isSuccess(), "Create Success status should be true");
        assertNotNull(response.getData(), "Create Product data object should not be null");
        assertNotNull(response.getData().getReference(), "Create Reference should not be null");
        UpdateProductDTO updateDTO  = new UpdateProductDTO("Table price updated", new BigDecimal(14), null, PricingType.PricePerItem, null, null, response.getData().getReference());
        BaseResponse<Product> updateResponse  = productServicePort.update(updateDTO);

        assertNotNull(updateResponse, "Update Product response should not be null");
        assertTrue(updateResponse.isSuccess(), "Update Success status should be true");
        assertNotNull(updateResponse.getData(), "Update Product data object should not be null");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(14.0)), updateResponse.getData().getUnitPrice(), "Update response Product Unit price should be 14");
        Optional<Product> updated = productServicePort.findById(updateResponse.getData().getReference());
        assertTrue(updated.isPresent(), "Product should be persisted");
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(14)), updated.get().getUnitPrice(), "Persisted Product update Unit price should be 14");
    }


}