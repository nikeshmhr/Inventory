package com.nikesh.inventoryapi.exception;

import com.nikesh.inventoryapi.dto.response.ErrorResponse;

/**
 *
 * @author Nikesh Maharjan
 */
public class GenericException extends RuntimeException {

    private ErrorResponse errorResponse;

    public GenericException(ErrorResponse errorResponse) {
        super(errorResponse.getErrorMessage());

        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}
