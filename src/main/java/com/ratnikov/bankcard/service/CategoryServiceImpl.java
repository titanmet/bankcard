package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CategoryDTO;
import com.ratnikov.bankcard.mapper.CategoryMapper;
import com.ratnikov.bankcard.model.Category;
import com.ratnikov.bankcard.model.User;
import com.ratnikov.bankcard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO findCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        return categoryMapper.categoryToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<CategoryDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.categoryRepository.findAll(pageable).map(categoryMapper::categoryToDTO);
    }

    @Override
    public List<CategoryDTO> getByKeyword(String keyword) {
        return categoryRepository.findByKeyword(keyword)
                .stream()
                .map(categoryMapper::categoryToDTO)
                .collect(Collectors.toList());
    }
}