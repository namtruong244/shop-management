package com.shopmanagement.repository;

import com.google.cloud.firestore.Firestore;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends BaseRepository<Product> {

    public ProductRepository(Firestore firestore) {
        super(firestore, CmnConst.PRODUCTS_COLLECTION);
    }

}
