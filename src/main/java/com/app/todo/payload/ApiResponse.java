package com.app.todo.payload;

public class ApiResponse {

    private boolean flag;
    private String message;

    public ApiResponse() {
        super();
    }

    public ApiResponse(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                '}';
    }
}