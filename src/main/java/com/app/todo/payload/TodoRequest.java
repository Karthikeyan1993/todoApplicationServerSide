package com.app.todo.payload;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TodoRequest {
    private String name;
    private String tag;
    private String priority;
    private String status;
    private Date duedate;
}
