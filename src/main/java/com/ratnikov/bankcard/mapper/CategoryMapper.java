package com.ratnikov.bankcard.mapper;

import com.ratnikov.bankcard.dto.CategoryDTO;
import com.ratnikov.bankcard.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToDTO(Category category);

    List<CategoryDTO> toCategoryDTOs(List<Category> categories);

    Category categoryToModel(CategoryDTO categoryDTO);
}
