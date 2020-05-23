package com.app.todo.controller;

import com.app.todo.Exception.TodoException;
import com.app.todo.entity.Todo;
import com.app.todo.payload.TodoRequest;
import com.app.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class TodoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("todos")
    public ResponseEntity<List<Todo>> getAllTodoEntity(Authentication authentication) {
        LOGGER.debug("Request Received For Getting All Todo Information");
        return new ResponseEntity<>(this.todoService.getAllTodo(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("todos/{id}")
    public ResponseEntity<Todo> findTodoById(@PathVariable Long id) throws TodoException {
        LOGGER.debug("Request Received To Get Todo By Id {}", id);
        Optional<Todo> optional = this.todoService.findTodo(id);
        if (optional.isPresent()) {
            Todo todo = optional.get();
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            throw new TodoException("Todo does not exits");
        }
    }

    @PostMapping("todo")
    public ResponseEntity<Todo> saveTodoEntity(@RequestBody TodoRequest todoRequest, Authentication auth) {
        LOGGER.debug("Request Received For Save New Todo Item");
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setPriority(todoRequest.getPriority());
        todo.setStatus(todoRequest.getStatus());
        todo.setTag(todoRequest.getTag());
        todo.setDuedate(todoRequest.getDuedate());
        todo.setCreatedBy(auth.getName());
        return new ResponseEntity<>(todoService.saveTodo(todo), HttpStatus.OK);
    }

    @PutMapping("todo")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        LOGGER.debug("Request Received To Todo Update");
        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("todo/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) throws TodoException {
        LOGGER.debug("Request Received To Delete Todo {}", id);
        Optional<Todo> optional = this.todoService.findTodo(id);
        if (optional.isPresent()) {
            this.todoService.deleteTodo(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            throw new TodoException("Todo does not exits");
        }
    }

}
