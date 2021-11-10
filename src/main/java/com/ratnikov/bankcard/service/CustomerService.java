package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Customer> getByKeyword(String keyword);
    Customer findCustomerByCustomerName(String customerName);
    Customer findCustomerByEmail(String email);
    Customer findCustomerByPhone(String phone);
}
