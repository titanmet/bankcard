package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CategoryDTO;
import com.ratnikov.bankcard.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    List<CategoryDTO> getByKeyword(String keyword);

    CategoryDTO findCategoryByName(String name);

    List<CategoryDTO> findAll();

    void save(Category category);

    void deleteById(Long id);
}
