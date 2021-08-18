package com.shopmanagement.service;

import com.shopmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<ProductRepository>{

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }


}
