package com.microservice.order_service.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.LinkedHashMap;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
//    Bất kì ngoại lệ nào xảy ra thì đều nhảy  vào method này
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        var message = "Internal server error";
        var response = new ErrorResponse(message, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var message = "Tài khoản hoặc mật khẩu không đúng";
        var error = new ErrorResponse(message, null);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);
    }
    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        var message = "Quyền truy cập bị từ chối";
        var error = new ErrorResponse(message, null);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);
    }

    //405 Phương thức truyền lên không được hỗ trợ
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var message = "Method not supported";
        var response = new ErrorResponse(message, null);
        return new ResponseEntity<>(response, headers, status);
    }

    //415 Loại không được hỗ trợ(kiểu dữ liệu truyền lên là json mfa truyền  text...  nên lỗi)
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var message = "Media type not supported";
        var response = new ErrorResponse(message, null);
        return new ResponseEntity<>(response, headers, status);
    }

//    Thiếu tham số  truyền vào
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var message = "Missing request parameter";
        var response = new ErrorResponse(message, null);
        return new ResponseEntity<>(response, headers, status);
    }

//    Truyền lên kiểu dữ liẹu trên đường dãn nhưng không đúng kiểu
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (exception instanceof MethodArgumentTypeMismatchException e) {
            var requiredType = e.getRequiredType();
            var message = "Type mismatch";
            var response = new ErrorResponse(message, null);
            return new ResponseEntity<>(response, headers, status);
        }
        return super.handleTypeMismatch(exception, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var message = "Dữ liệu không hợp lệ";
        var details = new LinkedHashMap<String, String>();
        for (var error : ex.getFieldErrors()) {
            var key = error.getField();
            var value = error.getDefaultMessage();
            details.put(key, value);
        }

        var errorResponse = new ErrorResponse(message, details);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex
    ){
        var message = "Dữ liệu không hợp lệ";
        var details = new LinkedHashMap<String, String>();
        for (var error : ex.getConstraintViolations()) {
            var key = error.getPropertyPath().toString();
            var value = error.getMessage();
            details.put(key, value);
        }
        var errorResponse = new ErrorResponse(message, details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
