package com.app.todo.service;

import com.app.todo.entity.Todo;
import com.app.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(@Qualifier("todoRepository") TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

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
    public void deleteTodo(Long id) {
        this.todoRepository.deleteById(id);
    }

    @Override
    public Todo updateTodo(Todo todo) {
        return this.todoRepository.saveAndFlush(todo);
    }

}
