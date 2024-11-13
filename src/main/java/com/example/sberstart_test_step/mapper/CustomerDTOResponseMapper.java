package com.example.sberstart_test_step.mapper;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.model.base.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ComponentModelGem.SPRING;

@Mapper(componentModel = SPRING, uses = CustomerDTORequestMapper.class)
public interface CustomerDTOResponseMapper extends BaseMapper<Customer, CustomerDTOResponse> {
    CustomerDTOResponseMapper INSTANCE = Mappers.getMapper(CustomerDTOResponseMapper.class);

    @Mapping(source = "firstName", target = "request.firstName")
    @Mapping(source = "lastName", target = "request.lastName")
    @Mapping(source = "email", target = "request.email")
    @Mapping(source = "phone", target = "request.phone")
    @Mapping(source = "passport", target = "request.passport")
    @Mapping(source = "birthday", target = "request.birthday")
    CustomerDTOResponse toDto(Customer customer);
}
