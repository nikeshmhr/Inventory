package com.nikesh.inventoryapi.dto.response;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Nikesh Maharjan
 */
public class ErrorResponse implements Serializable {

    private int errorCode;
    private HttpStatus status;
    private String errorMessage;
    private String developerMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(int errorCode, HttpStatus status, String errorMessage, String developerMessage) {
        this.errorCode = errorCode;
        this.status = status;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

}
