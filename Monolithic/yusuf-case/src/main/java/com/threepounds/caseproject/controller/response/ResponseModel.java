package com.threepounds.caseproject.controller.response;


public class ResponseModel<T> {

    private int statusCode;

    private T body;

    private String errorMessage;

    private int totalElements;

    private int totalPages;

    public ResponseModel() {
    }

    public ResponseModel(int statusCode, T body, String errorMessage, int totalElements, int totalPages) {
        this.statusCode = statusCode;
        this.body = body;
        this.errorMessage = errorMessage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public ResponseModel(int statusCode, T body, String errorMessage) {
        this.statusCode = statusCode;
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
