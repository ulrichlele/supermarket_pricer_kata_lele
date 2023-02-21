package io.lele.supermarket.pricer.application.port.in;

import io.lele.supermarket.pricer.application.port.in.dto.promotion.CreatePromotionDTO;
import io.lele.supermarket.pricer.application.port.in.dto.promotion.UpdatePromotionDTO;
import io.lele.supermarket.pricer.domain.Promotion;

import java.util.Optional;
import java.util.Set;

public interface PromotionRequestPort {

    Promotion create(CreatePromotionDTO model);

    Optional<Promotion> findById(String reference);

    Promotion update(UpdatePromotionDTO model);

    boolean recordExists(String reference);

    Promotion findRecordById(String reference);

    Set<Promotion> findAll();
}
