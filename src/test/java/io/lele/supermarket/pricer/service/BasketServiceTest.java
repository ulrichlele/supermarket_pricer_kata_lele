package io.lele.supermarket.pricer.service;

import io.lele.supermarket.pricer.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.model.Basket;
import io.lele.supermarket.pricer.model.BasketItem;
import io.lele.supermarket.pricer.model.Product;
import io.lele.supermarket.pricer.model.enums.*;
import io.lele.supermarket.pricer.utils.AmountUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketServiceTest {

    private static BasketService basketService;

    @BeforeAll
    @DisplayName("Initialize products and pricer")
    static void init(){
        basketService = new BasketService();
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 45, Qty = 3, UP=15")
    void evaluateBasket45USDOf3ItemsOf15USDFixedPrice() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(45)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 0, UP=15")
    void evaluateBasket45USDOf0temsOf15USDFixedPrice() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(15));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0));
        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(0)), basket.getTotalPrice());
    }
    @Test
    @DisplayName("Eval Basket - Flat Amt - exp 0, Qty = 3, UP=0")
    void evaluateBasket45USDOf3ItemsOf0USDFixedPrice() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(0));
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(3));
        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(0)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket -three-for-one-dollar - exp 2, Qty = 6, UP=1")
    void evaluateBasketTreeForOneDollar() throws IncompatibleUnitsException {
        Product   product = new Product("Table ", new BigDecimal(1));
        product.setPricedQuantity(new BigDecimal(3) );
        product.setPricingType(PricingType.PriceOnQuantity);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(6));
        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(2)), basket.getTotalPrice());
    }


    @Test
    @DisplayName("Eval Basket - Length pricing - exp 18, Qty = 6m, UP=3")
    void evaluateBasket6mOfTissuePriced3USDPerMetter() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(6), PhysicalQuantity.Length, LengthUnitOfMeasurement.Meter);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(18)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Length pricing - exp 1.2, Qty = 40cm, UP=3")
    void evaluateBasket40cmOfTissuePriced3USDPerMetter() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(40), PhysicalQuantity.Length, LengthUnitOfMeasurement.Centimeter);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1.2)), basket.getTotalPrice());
    }


    @Test
    @DisplayName("Eval Basket - Length pricing - exp 1500, Qty = 0.5km, UP=3")
    void evaluateBasketHalfkmOfTissuePriced3USDPerMetter() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, LengthUnitOfMeasurement.Meter);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0.5, new MathContext(0)), PhysicalQuantity.Length, LengthUnitOfMeasurement.Kilometer);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1500)), basket.getTotalPrice());
    }


    //-------------Mass pricing
    @Test
    @DisplayName("Eval Basket - Mass pricing - exp 18, Qty = 6g, UP=3")
    void evaluateBasket6gOfTomatoPriced3USDPerGram() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, MassUnitOfMeasurement.Gram);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(6), PhysicalQuantity.Mass, MassUnitOfMeasurement.Gram);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(18)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Mass pricing - exp 1.2, Qty = 40mg, UP=3")
    void evaluateBasket40mgOfTissuePriced3USDPerGram() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, MassUnitOfMeasurement.Gram);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(40), PhysicalQuantity.Mass, MassUnitOfMeasurement.Milligram);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(new BigDecimal(0.12, new MathContext(2)), basket.getTotalPrice());
    }


    @Test
    @DisplayName("Eval Basket - Mass pricing - exp 1500, Qty = 0.5kg, UP=3")
    void evaluateBasketHalfkgOfTissuePriced3USDPerGram() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, MassUnitOfMeasurement.Gram);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0.5, new MathContext(0)), PhysicalQuantity.Mass, MassUnitOfMeasurement.Kilogram);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1500)), basket.getTotalPrice());
    }

    //-------------Area pricing

    @Test
    @DisplayName("Eval Basket - Area pricing - exp 18, Qty = 6msq, UP=3")
    void evaluateBasket6msqOfTissuePriced3USDPerMetterSq() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, AreaUnitOfMeasurement.MeterSquare);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(6), PhysicalQuantity.Mass, AreaUnitOfMeasurement.MeterSquare);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(18)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Area pricing - exp 1.2, Qty = 40cmsq, UP=3")
    void evaluateBasket40cmsqOfTissuePriced3USDPerMetterSq() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, AreaUnitOfMeasurement.MeterSquare);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(40), PhysicalQuantity.Mass, AreaUnitOfMeasurement.CentimeterSquare);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1.2)), basket.getTotalPrice());
    }


    @Test
    @DisplayName("Eval Basket - Area pricing - exp 1500, Qty = 0.5kmsq, UP=3")
    void evaluateBasketHalfkmsqOfTissuePriced3USDPerMettersq() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, AreaUnitOfMeasurement.MeterSquare);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0.5, new MathContext(0)), PhysicalQuantity.Mass, AreaUnitOfMeasurement.KilometerSquare);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1500)), basket.getTotalPrice());
    }



    //-------------Volume pricing
    @Test
    @DisplayName("Eval Basket - Volume pricing - exp 18, Qty = 6l, UP=3")
    void evaluateBasket6lOfTomatoPriced3USDPerLitre() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, VolumeUnitOfMeasurement.Litre);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(6), PhysicalQuantity.Volume, VolumeUnitOfMeasurement.Litre);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(18)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Volume pricing - exp 1.2, Qty = 40ml, UP=3")
    void evaluateBasket40mlOfTissuePriced3USDPerLitre() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, VolumeUnitOfMeasurement.Litre);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(40), PhysicalQuantity.Volume, VolumeUnitOfMeasurement.Millilitre);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(new BigDecimal(0.12, new MathContext(2)), basket.getTotalPrice());
    }


    @Test
    @DisplayName("Eval Basket - Volume pricing - exp 1500, Qty = 0.5kl, UP=3")
    void evaluateBasketHalfklOfTissuePriced3USDPerLitre() throws IncompatibleUnitsException {
        Product   product = new Product("Tissue ", new BigDecimal(3), null, PricingType.PricePerUnitOfMeasurement, VolumeUnitOfMeasurement.Litre);
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0.5, new MathContext(0)), PhysicalQuantity.Volume, VolumeUnitOfMeasurement.Kilolitre);

        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(1500)), basket.getTotalPrice());
    }

    @Test
    @DisplayName("Eval Basket - Flat Amt - Promo - exp 0, Qty = 0, UP=15")
    void evaluateBasket45USDOf0temsOf15USDFixedPrices() throws IncompatibleUnitsException {
        Product   product = new Product("Chair ", new BigDecimal(15));
        //product.setPromotion();
        Basket basket = new Basket();
        BasketItem item  = new BasketItem(basket, product, new BigDecimal(0));
        basket.getItems().add(item);
        basketService.evaluatePrice(basket);
        assertEquals(AmountUtil.scaleAmount(new BigDecimal(0)), basket.getTotalPrice());
    }
}