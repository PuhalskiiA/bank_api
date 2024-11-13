package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.card.BalanceCardDTOResponse;
import com.example.sberstart_test_step.model.base.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING)
public interface BalanceCardDTOResponseMapper extends BaseMapper<Card, BalanceCardDTOResponse> {
    BalanceCardDTOResponseMapper INSTANCE = Mappers.getMapper(BalanceCardDTOResponseMapper.class);

    @Mapping(source = "id", target = "cardId", qualifiedByName = "getId")
    BalanceCardDTOResponse toDTO(Card card);

    @Named("getId")
    static UUID getId(UUID id) {
        return id;
    }

}
