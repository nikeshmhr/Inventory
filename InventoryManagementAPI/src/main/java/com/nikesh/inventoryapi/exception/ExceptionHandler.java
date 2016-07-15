package com.nikesh.inventoryapi.exception;

import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author Nikesh Maharjan
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = GenericException.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        GenericException exception = (GenericException) ex;
        ErrorResponse errorResponse = exception.getErrorResponse();

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorMessage("Sorry, something went wrong during operation.");
        errorResponse.setDeveloperMessage(ex.getMessage());
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}
