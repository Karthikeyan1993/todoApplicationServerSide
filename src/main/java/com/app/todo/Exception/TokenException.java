package com.app.todo.Exception;

public class TokenException extends Exception{
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public TokenException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public TokenException() {
        super();
    }
}
