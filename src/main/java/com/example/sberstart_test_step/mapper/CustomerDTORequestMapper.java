package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.card.CardDTORequest;
import com.example.sberstart_test_step.model.base.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING)
public interface CustomerDTORequestMapper extends BaseMapper<Card, CardDTORequest> {
    CustomerDTORequestMapper INSTANCE = Mappers.getMapper(CustomerDTORequestMapper.class);
}
