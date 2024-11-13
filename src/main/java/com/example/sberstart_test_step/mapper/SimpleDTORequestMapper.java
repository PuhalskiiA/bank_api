package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.card.CardDTORequest;
import com.example.sberstart_test_step.dto.card.SimpleCardDTORequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING, uses = CustomerDTORequestMapper.class)
public interface SimpleDTORequestMapper extends BaseMapper<CardDTORequest, SimpleCardDTORequest> {
    SimpleDTORequestMapper INSTANCE = Mappers.getMapper(SimpleDTORequestMapper.class);
}
