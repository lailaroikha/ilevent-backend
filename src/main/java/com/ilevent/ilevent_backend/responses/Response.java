package com.ilevent.ilevent_backend.responses;

import com.ilevent.ilevent_backend.users.dto.RegisterResponseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
@ToString
public class Response<T> {
    private int statusCode;
    private String message;
    boolean success = false;
    private T data;

    public Response(int statCode, String statusDesc) {
        statusCode = statCode;
        message = statusDesc;

        if (statusCode == HttpStatus.OK.value()) {
            success = true;
        }

    }

    public static <T> ResponseEntity<Response<Object>> failed(String message) {
        return failed(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> ResponseEntity<Response<T>> failed(T data) {
        return failed(HttpStatus.BAD_REQUEST.value(), "Bad request", data);
    }

    public static <T> ResponseEntity<Response<T>> failed(int statusCode, String message) {
        return failed(statusCode, message, null);
    }

    public static <T> ResponseEntity<Response<T>> failed(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(false);
        response.setData(data);
        return ResponseEntity.status(statusCode).body(response);
    }

    public static <T> ResponseEntity<Response<T>> success(String message, T data) {
        return success(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResponseEntity<Response<T>> success(String message) {
        return success(HttpStatus.OK.value(), message, null);
    }

    public static <T> ResponseEntity<Response<T>> success(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(statusCode).body(response);
    }

//    public static <T>  ResponseEntity<Response<T>>successfulResponse(int statusCode, String message, T data)
////            String userProfileSetupSuccessfully, RegisterResponseDto byEmail)
//    {
//        Response<T> response = new Response<>(statusCode, message);
//        response.setSuccess(true);
//        response.setData(data);
//        return ResponseEntity.status(statusCode).body(response);
//    }
}
