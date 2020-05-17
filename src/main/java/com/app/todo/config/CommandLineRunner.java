package com.app.todo.config;

import com.app.todo.TodoApplication;
import com.app.todo.entity.Todo;
import com.app.todo.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoApplication.class);

    private final TodoRepository todoRepository;

    public CommandLineRunner(@Qualifier("todoRepository") TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) {
        LOGGER.debug("Initial Testing Data Loaded");
        Todo t1 = new Todo("Testing Name", "Testing", "High", "In Progress", new Date());
        todoRepository.save(t1);
    }
}