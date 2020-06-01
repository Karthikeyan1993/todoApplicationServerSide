package com.app.todo.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
}
