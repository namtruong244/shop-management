package com.shopmanagement.exception;

public class EntityExistedException extends RuntimeException {

    public EntityExistedException(Class clazz, String params) {
        super(clazz.getSimpleName() + " is existed with id " + params);
    }
}
