package com.app.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.todo.entity.Todo;
import com.app.todo.service.TodoService;

@RestController
@RequestMapping("api/v1")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping("todos")
	public ResponseEntity<List<Todo>> getTodoEntity() {
		return new ResponseEntity<List<Todo>>(this.todoService.getAllTodo(), HttpStatus.OK);
	}

	@PostMapping("save/todo")
	public ResponseEntity<Todo> saveTodoEntity(@RequestBody Todo todo) {
		return new ResponseEntity<Todo>(this.todoService.saveTodo(todo), HttpStatus.OK);
	}

}
