package com.shopmanagement.repository;

import com.google.cloud.firestore.Firestore;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository(Firestore firestore) {
        super(firestore, CmnConst.USERS_COLLECTION);
    }

}
