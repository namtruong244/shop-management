package com.shopmanagement.repository;

import com.google.cloud.firestore.Firestore;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.Warehouse;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseRepository extends BaseRepository<Warehouse> {

    public WarehouseRepository(Firestore firestore) {
        super(firestore, CmnConst.WAREHOUSE_COLLECTION);
    }

}
