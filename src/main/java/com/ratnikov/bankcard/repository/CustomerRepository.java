package com.ratnikov.bankcard.repository;

import com.ratnikov.bankcard.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByCustomerName(String customerName);
    Customer findByEmail(String email);
    Customer findByPhone(String phone);

    @Query(value = "select * from customer c where c.customer_name like %:keyword%", nativeQuery = true)
    List<Customer> findByKeyword(@Param("keyword") String keyword);
}
