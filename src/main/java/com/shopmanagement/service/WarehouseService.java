package com.shopmanagement.service;

import com.shopmanagement.entity.Warehouse;
import com.shopmanagement.repository.WarehouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends BaseService<WarehouseRepository> {

    public WarehouseService(WarehouseRepository repository) {
        super(repository);
    }

    public ResponseEntity<?> saveWarehouseByProductId(Warehouse warehouse, String productId){
        warehouse.setProductId(productId);
        return saveDocument(warehouse);
    }
}
