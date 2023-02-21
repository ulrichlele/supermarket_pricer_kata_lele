package io.lele.supermarket.pricer.adapter.in.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lele.supermarket.pricer.application.port.in.ProductRequestPort;
import io.lele.supermarket.pricer.application.port.in.PromotionRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.product.CreateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.product.UpdateProductDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.CreatePromotionDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.UpdatePromotionDTO;
import io.lele.supermarket.pricer.domain.Product;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.domain.enums.PricingType;
import io.lele.supermarket.pricer.domain.enums.PromotionEligibilityBase;
import io.lele.supermarket.pricer.domain.enums.PromotionOfferType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PromotionWSTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private PromotionRequestPort promotionServicePort;



    @Test
    void createPromotion() throws Exception{
        Random rand = new Random();
        Integer unitPrice = rand.nextInt(50000);
       CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);
        mockMvc.perform(post("/api/promotion")
                        .content(asJsonString(model))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.offerType", is(PromotionOfferType.PriceReduction.name())))
                .andExpect(jsonPath("$.priceReductionType", is(PriceReductionType.Percentage.name())));
    }

    @Test
    void getPromotion() throws Exception{

        CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);

        Promotion domain = promotionServicePort.create(model);
        assertNotNull(domain, "Domain object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        mockMvc.perform(get("/api/promotion/"+domain.getReference())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.offerType", is(PromotionOfferType.PriceReduction.name())));
    }

    @Test
    void updatePromotion() throws Exception{

        CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);

        Promotion domain = promotionServicePort.create(model);
        assertNotNull(domain, "Domain object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Random rand = new Random();
        Integer unitPrice = rand.nextInt(50000);

        UpdatePromotionDTO updateDto = new UpdatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(30), PriceReductionType.Flat, domain.getReference());
         mockMvc.perform(put("/api/promotion")
                        .content(asJsonString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.offer", is(30)))
                 .andExpect(jsonPath("$.priceReductionType", is(PriceReductionType.Flat.name())));
    }


    @Test
    void findAllItems() throws Exception{
        CreatePromotionDTO model = new CreatePromotionDTO(PromotionEligibilityBase.PurchaseQuantity, BigDecimal.valueOf(25), PromotionOfferType.PriceReduction, BigDecimal.valueOf(3), PriceReductionType.Percentage);

        Promotion domain = promotionServicePort.create(model);
        assertNotNull(domain, "Domain object should not be null");
        assertNotNull(domain.getReference(), "Reference should not be null");

        Random rand = new Random();
         mockMvc.perform(get("/api/promotion/all")
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