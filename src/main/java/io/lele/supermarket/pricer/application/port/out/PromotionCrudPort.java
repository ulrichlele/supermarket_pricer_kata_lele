package io.lele.supermarket.pricer.application.port.out;

import io.lele.supermarket.pricer.domain.Promotion;

import java.util.Optional;
import java.util.Set;

public interface PromotionCrudPort {

    Promotion create(Promotion domain);

    Promotion update(Promotion domain);

    Optional<Promotion> findById(String reference);

    boolean recordExists(String reference);

    Set<Promotion> findAll();
}
