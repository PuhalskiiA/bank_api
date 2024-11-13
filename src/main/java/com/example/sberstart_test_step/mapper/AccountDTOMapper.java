package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.AccountDTO;
import com.example.sberstart_test_step.model.base.Account;
import com.example.sberstart_test_step.model.base.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountDTOMapper extends BaseMapper<Account, AccountDTO> {
    AccountDTOMapper INSTANCE = Mappers.getMapper(AccountDTOMapper.class);

    @Named("getCustomerId")
    static UUID getCustomerId(Customer customer) {
        return customer.getId();
    }

    @Mapping(source = "customer", target = "customerId", qualifiedByName = "getCustomerId")
    @Mapping(source = "id", target = "accountId")
    AccountDTO toDto(Account account);
}
