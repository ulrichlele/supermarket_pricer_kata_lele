package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.base.BaseResponse;
import io.lele.supermarket.pricer.application.services.BasketItemService;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.UnitOfMeasurement;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServicePortTest {

    @Autowired
    private ProductServicePort productServicePort;

    @Test
    void createProductServicePort() {
        CreateProductModel model = new CreateProductModel("Table", new BigDecimal(-5), null, PricingType.PricePerItem, null);
        BaseResponse<Product> response = productServicePort.create(model);
        assertNotNull(response, "Create product response object");
        assertTrue(response.isSuccess(), "Success status should be true");
        assertNotNull(response.getData(), "Product data object should not be null");
        assertNotNull(response.getData().getReference(), "Reference should not be null");
    }


}