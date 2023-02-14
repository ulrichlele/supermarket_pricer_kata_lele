package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.adapter.out.jpa.repo.ProductRepository;
import io.lele.supermarket.pricer.application.port.out.ProductCrudPort;
import io.lele.supermarket.pricer.core.BaseResponse;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServicePortTest {

    @Autowired
    private ProductServicePort productServicePort;


    @Test
    void createProductServicePort() {
        CreateProductModel model = new CreateProductModel("Table", new BigDecimal(5), null, PricingType.PricePerItem, null, null);

        BaseResponse<Product> response = productServicePort.create(model);
        assertNotNull(response, "Create product response object");
        assertTrue(response.isSuccess(), "Success status should be true");
        assertNotNull(response.getData(), "Product data object should not be null");
        assertNotNull(response.getData().getReference(), "Reference should not be null");
        assertNotNull(response.getData(), "Reference should not be null");

        Optional<Product> existing = productServicePort.findById(response.getData().getReference());
        assertTrue(existing.isPresent(), "Product should be persisted");
    }


}