package io.lele.supermarket.pricer.application.services;

import io.lele.supermarket.pricer.application.exceptions.IncompatibleUnitsException;
import io.lele.supermarket.pricer.application.exceptions.InvalidProductPromotion;
import io.lele.supermarket.pricer.application.port.in.PromotionRequestPort;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.CreatePromotionDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.PromotionDtoMapper;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.UpdatePromotionDTO;
import io.lele.supermarket.pricer.application.port.out.PromotionCrudPort;
import io.lele.supermarket.pricer.core.RecordNotFound;
import io.lele.supermarket.pricer.core.ValidationException;
import io.lele.supermarket.pricer.core.ValidatorResult;
import io.lele.supermarket.pricer.domain.BasketItem;
import io.lele.supermarket.pricer.domain.Promotion;
import io.lele.supermarket.pricer.domain.enums.PriceReductionType;
import io.lele.supermarket.pricer.application.utils.AmountUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class PromotionService implements PromotionRequestPort {

    private UnitConverter converter;
    private PromotionCrudPort promotionCrudPort;

    public PromotionService(UnitConverter unitConverter, PromotionCrudPort promotionCrudPort){
        this.converter = unitConverter;
        this.promotionCrudPort = promotionCrudPort;
    }

    public void evaluateProductPromotion(BasketItem item) throws IncompatibleUnitsException, InvalidProductPromotion {
        if (item == null || item.getProduct() == null || item.getProduct().getPromotion() == null)
            return;
        Promotion promo = item.getProduct().getPromotion();
        if(promo == null)
            return;
        boolean isItemEligible = isBasketItemEligibleToPromotion(item, promo);
        if (!isItemEligible) return;
        switch (promo.getOfferType()) {
            case Quantity: applyPromotionQuantity(item, promo.getOffer()); break;
            case PriceReduction: applyPromotionPriceReduction(item, promo.getOffer(), promo.getPriceReductionType()); break;
        }
    }

    private void applyPromotionPriceReduction(BasketItem item, BigDecimal offer, PriceReductionType type) {
        switch (type) {
            case Percentage:
                BigDecimal percentagedPrice = (new BigDecimal(1).subtract(offer.divide(new BigDecimal(100)))).multiply(item.getPrice());
                item.setTotalPrice(AmountUtil.scaleAmount(percentagedPrice));
                item.setPromotion(offer);
                break;
            case Flat:
                BigDecimal reducedPrice = item.getPrice().subtract(offer);
                reducedPrice = reducedPrice.compareTo(BigDecimal.ZERO) >= 0 ? reducedPrice : BigDecimal.ZERO;
                item.setTotalPrice(AmountUtil.scaleAmount(reducedPrice));
                item.setPromotion(offer);
                break;
        }
    }
    private void applyPromotionQuantity(BasketItem item, BigDecimal offer) throws IncompatibleUnitsException {
        switch (item.getProduct().getPricingType()) {
            case PricePerItem:
                item.setOfferedQuantity(offer);
                item.setTotalQuantity(item.getQuantity().add(offer));
                break;
            case PricePerUnitOfMeasurement:
                BigDecimal offeredQty = converter.convert(offer, item.getProduct().getUnitOfMeasurement(), item.getUnitOfMeasurement());
                item.setOfferedQuantity(offeredQty);
                item.setTotalQuantity(item.getQuantity().add(offeredQty));
                break;
        }
    }

    boolean isBasketItemEligibleToPromotion(BasketItem item, Promotion promo) throws IncompatibleUnitsException {
        boolean isEligible = false;
        switch (promo.getEvaluationType()) {
            case Always:
                isEligible = true;
                break;
            case PurchaseAmount:
                isEligible = item.getPrice().compareTo(promo.getMinimumPurchase()) >= 0;
                break;
            case PurchaseQuantity:
                isEligible = isBasketItemEligibleToPromotionPurchaseQuantity(item, promo);
        }
        return isEligible;
    }

    boolean isBasketItemEligibleToPromotionPurchaseQuantity(BasketItem item, Promotion promo) throws IncompatibleUnitsException {
        boolean isEligible = false;
                switch (item.getProduct().getPricingType()) {
                    case PricePerItem:
                        isEligible = promo.getMinimumPurchase() == null || promo.getMinimumPurchase().equals(BigDecimal.ZERO) || item.getQuantity().compareTo(promo.getMinimumPurchase()) >= 0;
                        break;
                    case PricePerUnitOfMeasurement:
                        BigDecimal convertedQty = converter.convert(item.getQuantity(), item.getUnitOfMeasurement(), item.getProduct().getUnitOfMeasurement());
                        isEligible = convertedQty.compareTo(promo.getMinimumPurchase()) >= 0;
                        break;
                }
        return isEligible;
    }

    @Override
    public Promotion create(CreatePromotionDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        if(!validations.isEmpty())
            throw new ValidationException(Contants.STD_VALIDATION_FAILED, validations);
        Promotion domain = PromotionDtoMapper.INSTANCE.toCreateDomain(dto);
        domain = promotionCrudPort.update(domain);
        return domain;
    }

    @Override
    public Promotion update(UpdatePromotionDTO dto) {
        Set<ValidatorResult>  validations = dto.validate();
        if(!validations.isEmpty() && !this.recordExists(dto.getReference())){
            throw new RecordNotFound("Promotion: "+dto.getReference());
        }
        if(!validations.isEmpty())
            throw new ValidationException(Contants.STD_VALIDATION_FAILED, validations);
        Promotion domain = PromotionDtoMapper.INSTANCE.toUpdateDomain(dto);
        domain = promotionCrudPort.update(domain);
        return domain;
    }

    @Override
    public boolean recordExists(String reference) {
        return promotionCrudPort.recordExists(reference);
    }

    @Override
    public Optional<Promotion> findById(String reference) {
        return promotionCrudPort.findById(reference);
    }


    @Override
    public Promotion findRecordById(String reference) {
        return promotionCrudPort.findById(reference).orElseThrow(() -> new RecordNotFound(Contants.STD_RECORD_NOT_FOUND));
    }

    @Override
    public Set<Promotion> findAll() {
        return this.promotionCrudPort.findAll();
    }
}
