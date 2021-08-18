package com.shopmanagement.repository;

import com.google.cloud.firestore.Firestore;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends BaseRepository<Category> {

    public CategoryRepository(Firestore firestore) {
        super(firestore, CmnConst.CATEGORIES_COLLECTION);
    }

}
