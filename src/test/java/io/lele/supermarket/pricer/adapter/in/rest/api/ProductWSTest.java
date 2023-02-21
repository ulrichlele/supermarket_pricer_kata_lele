package io.lele.supermarket.pricer.adapter.in.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lele.supermarket.pricer.application.port.in.ProductRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import java.math.BigDecimal;
import java.util.Random;


@SpringBootTest
@AutoConfigureMockMvc
class ProductWSTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ProductRequestPort productServicePort;



    @Test
    void createProduct() throws Exception{
        Random rand = new Random();
        Integer unitPrice = rand.nextInt(50000);
        CreateProductDTO model = new CreateProductDTO("Tables", new BigDecimal(unitPrice), null, PricingType.PricePerItem, null, null);
        mockMvc.perform(post("/api/product")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Tables")))
                .andExpect(jsonPath("$.unitPrice", is(unitPrice.doubleValue())));
    }

    @Test
    void getProduct() throws Exception{

        CreateProductDTO createDto = new CreateProductDTO("Table", new BigDecimal(1458), null, PricingType.PricePerItem, null, null);

        Product domain = productServicePort.create(createDto);
        assertNotNull(domain, "Product object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        mockMvc.perform(get("/api/product/"+domain.getReference())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.unitPrice", is(1458.00)));
    }

    @Test
    void updateProduct() throws Exception{
        CreateProductDTO createDto = new CreateProductDTO("Table", new BigDecimal(100), null, PricingType.PricePerItem, null, null);

        Product domain = productServicePort.create(createDto);
        assertNotNull(domain, "Product object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Random rand = new Random();
        Integer unitPrice = rand.nextInt(50000);


        UpdateProductDTO updateDto = new UpdateProductDTO("Tables01", new BigDecimal(unitPrice), null, PricingType.PricePerItem, null, null, domain.getReference());
        mockMvc.perform(put("/api/product")
                        .content(asJsonString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Tables01")))
                .andExpect(jsonPath("$.unitPrice", is(unitPrice.doubleValue())));
    }


    @Test
    void findAllProduct() throws Exception{
        CreateProductDTO createDto = new CreateProductDTO("Table", new BigDecimal(100), null, PricingType.PricePerItem, null, null);

        Product domain = productServicePort.create(createDto);
        assertNotNull(domain, "Product object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Random rand = new Random();
        Integer unitPrice = rand.nextInt(50000);
         mockMvc.perform(get("/api/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].reference", notNullValue()));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}