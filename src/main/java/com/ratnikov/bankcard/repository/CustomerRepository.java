package com.ratnikov.bankcard.repository;

import com.ratnikov.bankcard.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);

    Optional<Customer> findById(Long id);

    @Query(value = "select * from customer c where c.name like %:keyword%", nativeQuery = true)
    List<Customer> findByKeyword(@Param("keyword") String keyword);
}