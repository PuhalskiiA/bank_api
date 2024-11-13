package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.dto.card.ChangeBalanceCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING)
public interface ChangeBalanceCardDTOMapper extends BaseMapper<CardDTOResponse, ChangeBalanceCardDTO> {
    ChangeBalanceCardDTOMapper INSTANCE = Mappers.getMapper(ChangeBalanceCardDTOMapper.class);
}
