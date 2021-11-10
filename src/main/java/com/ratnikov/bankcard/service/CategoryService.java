package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Category> getByKeyword(String keyword);
    Category findCategoryByName(String name);
}
