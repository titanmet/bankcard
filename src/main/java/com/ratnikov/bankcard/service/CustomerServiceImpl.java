package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CustomerDTO;
import com.ratnikov.bankcard.mapper.CustomerMapper;
import com.ratnikov.bankcard.model.Customer;
import com.ratnikov.bankcard.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Page<CustomerDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.customerRepository.findAll(pageable).map(customerMapper::customerToDTO);
    }

    @Override
    public List<CustomerDTO> getByKeyword(String keyword) {
        return customerRepository.findByKeyword(keyword)
                .stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name);
        return customerMapper.customerToDTO(customer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customerMapper.customerToDTO(customer);
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
