package io.lele.supermarket.pricer.adapter.out.jpa.repo;

import io.lele.supermarket.pricer.adapter.out.jpa.model.PromotionJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<PromotionJpa, String> {
}
