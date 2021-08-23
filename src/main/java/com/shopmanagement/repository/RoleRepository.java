package com.shopmanagement.repository;

import com.google.cloud.firestore.Firestore;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends BaseRepository<Role> {
    public RoleRepository(Firestore firestore) {
        super(firestore, CmnConst.ROLES_COLLECTION);
    }
}
