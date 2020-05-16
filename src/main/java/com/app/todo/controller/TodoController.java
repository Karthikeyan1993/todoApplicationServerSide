package com.app.todo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.todo.Exception.TodoException;
import com.app.todo.entity.Todo;
import com.app.todo.service.TodoService;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class TodoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

	@Autowired
	private TodoService todoService;

	@GetMapping("todos")
	public ResponseEntity<List<Todo>> getAllTodoEntity() {
		LOGGER.debug("Request Recieved For Getting All Todo Information");
		return new ResponseEntity<List<Todo>>(this.todoService.getAllTodo(), HttpStatus.OK);
	}

	@GetMapping("todos/{id}")
	public ResponseEntity<Todo> findTodoById(@PathVariable Long id) throws TodoException {
		LOGGER.debug("Request Recieved To Get Todo By Id {}", id);
		Optional<Todo> optional = this.todoService.findTodo(id);
		if (optional.isPresent()) {
			Todo todo = optional.get();
			return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		} else {
			throw new TodoException("Todo does not exits");
		}
	}

	@PostMapping("todo")
	public ResponseEntity<Todo> saveTodoEntity(@RequestBody Todo todo) {
		LOGGER.debug("Request Recieved For Save New Todo Item");
		return new ResponseEntity<Todo>(todoService.saveTodo(todo), HttpStatus.OK);
	}

	@PutMapping("todo")
	public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
		LOGGER.debug("Request Recieved To Todo Update");
		return new ResponseEntity<Todo>(this.todoService.updateTodo(todo), HttpStatus.OK);
	}

	@DeleteMapping("todo/{id}")
	public ResponseEntity<Boolean> deleteTodo(@PathVariable Long id) throws TodoException {
		LOGGER.debug("Request Recieved To Delete Todo {}", id);
		Optional<Todo> optional = this.todoService.findTodo(id);
		if (optional.isPresent()) {
			this.todoService.deleteTodo(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			throw new TodoException("Todo does not exits");
		}
	}

}
