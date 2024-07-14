//package com.ilevent.ilevent_backend.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//@ControllerAdvice
//public class CreateGlobalExceptionHandler {
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(EventNotFoundException.class)
//    public ResponseEntity<?> handleEventNotFoundException(EventNotFoundException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserNotOrganizerException.class)
//    public ResponseEntity<?> handleUserNotOrganizerException(UserNotOrganizerException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
