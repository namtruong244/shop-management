package com.shopmanagement.service;

import com.shopmanagement.common.CmnConst;
import com.shopmanagement.common.TranslatorCode;
import com.shopmanagement.component.Translator;
import com.shopmanagement.dto.ResponseDTO;
import com.shopmanagement.entity.BaseEntity;
import com.shopmanagement.entity.Product;
import com.shopmanagement.exception.EntityExistedException;
import com.shopmanagement.exception.EntityNotFoundException;
import com.shopmanagement.repository.BaseRepository;
import com.shopmanagement.util.GsonUtil;
import com.shopmanagement.util.StringUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseRepository<? extends BaseEntity>> {

    protected T repository;

    public BaseService(T repository) {
        this.repository = repository;
    }

    public String getEntityName(){
        return Translator.getMessage(repository.getEntity().getSimpleName());
    }

    public String getEntityIdName(){
        return Translator.getMessage(StringUtil.createTranslatorCode(repository.getEntity().getSimpleName(), CmnConst.ID_FIELD));
    }

    public ResponseEntity<?> findAllDocuments() {
        List<?> documents = repository.findAll();
        ResponseDTO<?> response = ResponseDTO.builder().data(documents).build();

        return ResponseEntity.ok(GsonUtil.toJson(response));
    }

    public ResponseEntity<?> findAllByField(String field, String param){
        List<?> documents = repository.findAllByField(field, param);
        ResponseDTO<?> response = ResponseDTO.builder().data(documents).build();
        return ResponseEntity.ok(GsonUtil.toJson(response));
    }

    public ResponseEntity<?> getDocumentById(String documentId) {
        return getDocumentByField(CmnConst.ID_FIELD, documentId);
    }

    public ResponseEntity<?> getDocumentByField(String fieldName, String param){
        Optional<?> entityInDB = repository.findByField(fieldName, param);
        if (entityInDB.isEmpty()) {
            throw new EntityNotFoundException(repository.getEntity(), CmnConst.ID_FIELD, param);
        }
        ResponseDTO<?> response = ResponseDTO.builder().data(entityInDB).build();
        return ResponseEntity.ok(GsonUtil.toJson(response));
    }

    public ResponseEntity<?> saveDocument(BaseEntity entity){
        if(entity.getId() != null){
            Optional<?> entityInDB = repository.findById(entity.getId());
            System.out.println(entityInDB.isEmpty());
            if (entityInDB.isPresent()) {
                throw new EntityExistedException(Product.class, entity.getId());
            }
        }
        ResponseDTO<?> response = ResponseDTO.builder().message(
                Translator.getMessageWithParam(TranslatorCode.SUCCESS_CREATE, getEntityName()))
                .build();
        repository.save(entity);
        return ResponseEntity.ok(GsonUtil.toJson(response));
    }

    public ResponseEntity<?> updateDocument(BaseEntity entity){
        Optional<?> entityInDB = repository.findById(entity.getId());
        if (entityInDB.isEmpty()) {
            throw new EntityNotFoundException(repository.getEntity(), CmnConst.ID_FIELD, entity.getId());
        }
        ResponseDTO<?> response = ResponseDTO.builder().message(
                Translator.getMessageWithParam(TranslatorCode.SUCCESS_UPDATE, getEntityName()))
                .build();
        entity.setCreateAt(((BaseEntity) entityInDB.get()).getCreateAt());
        repository.save(entity);
        return ResponseEntity.ok(GsonUtil.toJson(response));
    }

    public ResponseEntity<?> deleteDocument(String documentId) {
        Optional<?> entityInDB = repository.findById(documentId);
        if (entityInDB.isEmpty()) {
            throw new EntityNotFoundException(repository.getEntity(), CmnConst.ID_FIELD, documentId);
        }
        ResponseDTO<?> response = ResponseDTO.builder().message(
                Translator.getMessageWithParam(TranslatorCode.SUCCESS_DELETE, getEntityName(), getEntityIdName(), documentId))
                .build();
        repository.deleteById(documentId);
        return ResponseEntity.ok(GsonUtil.toJson(response));
    }
}
