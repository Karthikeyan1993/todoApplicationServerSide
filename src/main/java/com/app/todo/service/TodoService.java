package com.app.todo.service;

import java.util.List;
import java.util.Optional;

import com.app.todo.entity.Todo;

public interface TodoService {
	List<Todo> getAllTodo();

	Todo saveTodo(Todo todo);

	Optional<Todo> findTodo(Long id);

	void deleteTodo(Long id);

	Todo updateTodo(Todo todo);
}
