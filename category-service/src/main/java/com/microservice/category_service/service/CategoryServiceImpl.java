package com.microservice.category_service.service;


import com.microservice.category_service.dto.CategoryDto;
import com.microservice.category_service.entity.Category;
import com.microservice.category_service.form.CategoryCreateForm;
import com.microservice.category_service.form.CategoryFilterForm;
import com.microservice.category_service.form.CategoryUpdateForm;
import com.microservice.category_service.repository.CategoryRepository;
import com.microservice.category_service.specification.CategorySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryCreateForm categoryCreateForm) {
        var category = modelMapper.map(categoryCreateForm, Category.class);
        var savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryUpdateForm categoryUpdateForm, long id) {
        var optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        var category = optional.get();
        modelMapper.map(categoryUpdateForm, category);
        var savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategory(long id) {
        var optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Category not found");
        }
        return modelMapper.map(optional.get(), CategoryDto.class);
    }

    @Override
    public Page<CategoryDto> getAllCategories(CategoryFilterForm categoryFilterForm, Pageable pageable) {
        var spec = CategorySpecification.buildSpec(categoryFilterForm);
        return categoryRepository.findAll(spec,pageable).map(category -> modelMapper.map(category, CategoryDto.class));
    }
}
