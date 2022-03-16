package com.ratnikov.bankcard.mapper;

import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToDTO(Customer customer);

    List<CustomerDTO> toCustomerDTOs(List<Customer> customers);

    Customer customerToModel(CustomerDTO customerDTO);

    CustomerDTO customerToDTO(Optional<Customer> customer);
}
