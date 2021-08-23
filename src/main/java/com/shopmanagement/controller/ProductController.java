package com.shopmanagement.controller;

import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Warehouse;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final WarehouseService warehouseService;

    @GetMapping(value = "products")
    public ResponseEntity<?> getAllProduct() {
        return productService.findAllDocuments();
    }

    @GetMapping("products/{productId}/warehouses")
    public ResponseEntity<?> getAllWarehouseByProductId(@PathVariable String productId){
        return warehouseService.findAllByField(CmnConst.PRODUCT_ID_FIELD, productId);
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId) {
        return productService.getDocumentById(productId);
    }

    @PostMapping("products")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid Product product) {
        return productService.saveDocument(product);
    }

    @PostMapping("products/{productId}/warehouses")
    public ResponseEntity<?> saveWarehouseByProductId(@RequestBody @Valid Warehouse warehouse, @PathVariable String productId){
        return warehouseService.saveWarehouseByProductId(warehouse, productId);
    }

    @PutMapping("products")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return productService.updateDocument(product);
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        return productService.deleteDocument(productId);
    }
}
