package com.ratnikov.bankcard.repository;

import com.ratnikov.bankcard.model.Category;
import com.ratnikov.bankcard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query(value = "select * from category c where c.name like %:keyword%", nativeQuery = true)
    List<Category> findByKeyword(@Param("keyword") String keyword);
}
