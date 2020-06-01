package com.app.todo.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {
    private boolean flag;
    private String message;
}
