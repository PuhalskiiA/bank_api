package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.model.base.Account;
import com.example.sberstart_test_step.model.base.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING)
public interface CardDTOResponseMapper extends BaseMapper<Card, CardDTOResponse> {
    CardDTOResponseMapper INSTANCE = Mappers.getMapper(CardDTOResponseMapper.class);

    @Named("getId")
    static UUID getId(UUID id) {
        return id;
    }

    @Named("getFirstName")
    static String getName(Account account) {
        return account.getCustomer().getFirstName();
    }

    @Mapping(source = "id", target = "cardId", qualifiedByName = "getId")
    @Mapping(source = "account", target = "customerName", qualifiedByName = "getFirstName")
    CardDTOResponse toDto(Card card);
}