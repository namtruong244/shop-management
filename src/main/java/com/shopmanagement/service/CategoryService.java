package com.shopmanagement.service;

import com.shopmanagement.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<CategoryRepository>{

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

}
