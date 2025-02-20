package com.expense.management.services;

import com.expense.management.exceptions.CategoryAlreadyExistsException;
import com.expense.management.models.Category;
import com.expense.management.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void createCategory(Category category){
        if(categoryRepository.existsByName(category.getName())){
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
