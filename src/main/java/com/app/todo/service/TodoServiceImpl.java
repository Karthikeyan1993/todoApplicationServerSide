package com.app.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.todo.entity.Todo;
import com.app.todo.repository.TodoRepository;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public List<Todo> getAllTodo() {
		return this.todoRepository.findAll();
	}

	@Override
	public Todo saveTodo(Todo todo) {
		return this.todoRepository.save(todo);
	}

	@Override
	public Optional<Todo> findTodo(Long id) {
		return this.todoRepository.findById(id);
	}

	@Override
	public void delteTodo(Long id) {
		this.todoRepository.deleteById(id);
	}

	@Override
	public Todo updateTodo(Todo todo, Long id) {
		return this.todoRepository.saveAndFlush(todo);
	}

}
