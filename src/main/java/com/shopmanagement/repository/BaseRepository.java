package com.shopmanagement.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.shopmanagement.common.CmnConst;
import com.shopmanagement.common.TranslatorCode;
import com.shopmanagement.component.Translator;
import com.shopmanagement.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unchecked")
public abstract class BaseRepository<T extends BaseEntity> {
    private final CollectionReference collectionReference;
    private final Class<T> parameterizedType;
    private final String collectionName;

    public BaseRepository(Firestore firestore, String collectionName) {
        this.collectionReference = firestore.collection(collectionName);
        this.collectionName = collectionName;
        this.parameterizedType = getParameterizedType();
    }

    private Class<T> getParameterizedType(){
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Find all documents from collection
     *
     * @return
     */
    public List<T> findAll() {
        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionReference
                    .whereEqualTo(CmnConst.DELETE_FLG_FIELD, CmnConst.DELETE_FLG_OFF)
                    .get();
            List<QueryDocumentSnapshot> docs = querySnapshot.get().getDocuments();
            return docs.stream().map(doc -> doc.toObject(parameterizedType)).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            log.error(Translator.getMessageWithParam(TranslatorCode.ERROR_GET_ALL_DOCUMENT, collectionName));
        }
        return Collections.emptyList();
    }

    public List<T> findAllByField(String field, String param) {
        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionReference
                    .whereEqualTo(field, param)
                    .whereEqualTo(CmnConst.DELETE_FLG_FIELD, CmnConst.DELETE_FLG_OFF)
                    .get();
            List<QueryDocumentSnapshot> docs = querySnapshot.get().getDocuments();
            return docs.stream().map(doc -> doc.toObject(parameterizedType)).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            log.error(Translator.getMessageWithParam(TranslatorCode.ERROR_GET_ALL_DOCUMENT, collectionName));
        }
        return Collections.emptyList();
    }

    /**
     * Find document by id field
     * @param documentId Id of document
     * @return Optional of document
     */
    public Optional<T> findById(String documentId) {
        return findByField(CmnConst.ID_FIELD, documentId);
    }

    public Optional<T> findByField(String field, String param){
        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionReference.whereEqualTo(field, param)
                    .whereEqualTo(CmnConst.DELETE_FLG_FIELD, CmnConst.DELETE_FLG_OFF).limit(1).get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            if (documents.size() > 0) {
                return Optional.of(documents.get(0).toObject(parameterizedType));
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error(Translator.getMessageWithParam(TranslatorCode.ERROR_GET_DOCUMENT_BY_ID, collectionName,
                    param, e.getMessage()));
        }
        return Optional.empty();
    }

    /**
     * Count all document in collection
     *
     * @return
     */
    public int getTotalDocument() {
        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionReference
                    .whereEqualTo(CmnConst.DELETE_FLG_FIELD, CmnConst.DELETE_FLG_OFF).get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            return documents.size();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Save document
     *
     * @param data
     * @return
     */
    public boolean save(T data) {
        try {
            if (data.getId() == null){
                data.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            ApiFuture<WriteResult> writeResult = collectionReference.document(data.getId()).set(data);
            log.info("{}-{} saved at{}", collectionName, data.getId(), writeResult.get().getUpdateTime());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error(Translator.getMessageWithParam(TranslatorCode.ERROR_SAVE_DOCUMENT, collectionName,
                    data.getId(), e.getMessage()));
        }
        return false;
    }

    /**
     * Delete document by id
     *
     * @param documentId
     * @return
     */
    public boolean deleteById(String documentId) {
        try {
            ApiFuture<WriteResult> writeResult = collectionReference.document(documentId)
                    .update(CmnConst.DELETE_FLG_FIELD, CmnConst.DELETE_FLG_ON);
            log.info("{}-{} delete at{}", collectionName, documentId, writeResult.get().getUpdateTime());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error(Translator.getMessageWithParam(TranslatorCode.ERROR_DELETE_DOCUMENT, collectionName,
                    documentId, e.getMessage()));
        }
        return false;
    }

    protected CollectionReference getCollectionReference(){
        return this.collectionReference;
    }

    public String getCollectionName(){
        return this.collectionName;
    }

    public Class<T> getEntity(){
        return this.parameterizedType;
    }
}
