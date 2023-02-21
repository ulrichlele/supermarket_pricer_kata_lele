package io.lele.supermarket.pricer.application.port.in.dto.promotion;

import io.lele.supermarket.pricer.domain.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;

@Mapper
public interface PromotionDtoMapper {
    PromotionDtoMapper INSTANCE = Mappers.getMapper(PromotionDtoMapper.class);

    Promotion toUpdateDomain(UpdatePromotionDTO dto);

    Promotion toCreateDomain(CreatePromotionDTO dto);
}