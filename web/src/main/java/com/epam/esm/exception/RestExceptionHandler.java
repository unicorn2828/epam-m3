package com.epam.esm.exception;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the RestExceptionHandler class; it extends ResponseEntityExceptionHandler abstract class.
 * This class catches and handles raised exceptions.
 *
 * @author Vitaly Kononov
 * @version 1.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError customHandle(ServiceException ex) {
        return new ApiError(ex.getErrorMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ApiError handleAll(AccessDeniedException ex) {
        ServiceExceptionCode exception = ServiceExceptionCode.ACCESS_FORBIDDEN;
        return new ApiError(exception.getExceptionMessage(), exception.getExceptionCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleAll(Exception ex) {
        String message = ex.getMessage();
        ServiceExceptionCode errorCode = ServiceExceptionCode.UNKNOWN_EXCEPTION;
        return new ApiError(message, errorCode.getExceptionCode());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleAll(JwtException ex) {
        ServiceExceptionCode exception = ServiceExceptionCode.EXPIRED_OR_INVALID_JWT_TOKEN;
        return new ApiError(ex.getMessage(), exception.getExceptionCode());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleAll(ValidationException ex) {
        return new ApiError(ex.getMessage().substring(10), ex.getMessage().substring(0, 8));
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
        ServiceExceptionCode errorCode = ServiceExceptionCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION;
        return new ResponseEntity<>(new ApiError(ex.getMessage() + " : " + builder.substring(0, builder.length() - 2),
                                                 errorCode.getExceptionCode()), new HttpHeaders(), status);
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ServiceExceptionCode errorCode = ServiceExceptionCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION;
        return new ResponseEntity<>(new ApiError(builder.toString(), errorCode.getExceptionCode()),
                                    new HttpHeaders(), status);
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        String errorMessage = "unknown URL, no method found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ServiceExceptionCode errorCode = ServiceExceptionCode.NO_HANDLE_FOUND_EXCEPTION;
        ApiError apiError = new ApiError(errorMessage, errorCode.getExceptionCode());
        return new ResponseEntity<>(apiError, new HttpHeaders(), status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError customHandle(MethodArgumentTypeMismatchException ex) {
        String errorMessage = ex.getName() + " should be of type " + ex.getRequiredType().getSimpleName();
        ServiceExceptionCode errorCode = ServiceExceptionCode.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION;
        return new ApiError(errorMessage, errorCode.getExceptionCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                       violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ServiceExceptionCode errorCode = ServiceExceptionCode.CONSTRAINT_VIOLATION_EXCEPTION;
        return new ApiError(ex.getMessage(), errorCode.getExceptionCode());
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String message = ex.getMessage() + " : " + ex.getParameterName() + " parameter is missing";
        ServiceExceptionCode errorCode = ServiceExceptionCode.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION;
        return new ResponseEntity<>(new ApiError(message, errorCode.getExceptionCode()), new HttpHeaders(), status);
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ServiceExceptionCode errorCode = ServiceExceptionCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION;
        return new ResponseEntity<>(new ApiError(errorMessage, errorCode.getExceptionCode()), new HttpHeaders(), status);
    }
}
