package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Page<CustomerDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    List<CustomerDTO> getByKeyword(String keyword);

    CustomerDTO findCustomerByName(String customerName);

    List<CustomerDTO> findAll();

    CustomerDTO findCustomerById(Long id);

    void save(Customer customer);

    void deleteById(Long id);
}