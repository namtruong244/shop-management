package com.shopmanagement.exception;

import com.shopmanagement.component.Translator;
import com.shopmanagement.dto.ResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message(error)
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(response);
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ResponseDTO response = ResponseDTO.builder()
                .status(UNSUPPORTED_MEDIA_TYPE)
                .message(builder.substring(0, builder.length() - 2))
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message("Validation error")
                .build();
        response.addValidationErrors(ex.getBindingResult().getFieldErrors());
        response.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(METHOD_NOT_ALLOWED)
                .message(String.format("Method %s is not support for this request", ex.getMethod()))
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message(Translator.getMessage("error.validate"))
                .build();
        response.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(response);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ResponseDTO response = ResponseDTO.builder()
                .status(NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(EntityExistedException.class)
    protected ResponseEntity<Object> handleEntityExisted(
            EntityExistedException ex) {
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message("Malformed JSON request")
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("Error writing JSON output")
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("An error occurred. Please contact the administrator")
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(response);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(
            Exception ex) {
        ResponseDTO response = ResponseDTO.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("An error occurred. Please contact the administrator")
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(response);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ResponseDTO response = ResponseDTO.builder()
                .status(BAD_REQUEST)
                .message(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()))
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(response);
    }

    private ResponseEntity<Object> buildResponseEntity(ResponseDTO response) {
        return new ResponseEntity<>(response, response.getStatus());
    }

}
