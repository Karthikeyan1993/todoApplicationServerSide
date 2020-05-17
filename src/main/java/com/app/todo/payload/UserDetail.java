package com.app.todo.payload;

public class UserDetail {
    private String username;

    public UserDetail(){
        super();
    }

    public UserDetail(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
