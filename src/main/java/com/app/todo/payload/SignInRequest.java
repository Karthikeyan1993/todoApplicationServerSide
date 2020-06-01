package com.app.todo.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SignInRequest {
    private String userNameOrEmail;
    private String password;
}
