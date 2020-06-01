
package com.app.todo.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ResetPasswordRequest {
    private String userNameOrEmail;
    private String password;
    private String token;
}
