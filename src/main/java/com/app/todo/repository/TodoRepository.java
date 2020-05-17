package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.todo.entity.Todo;

import java.util.List;

@Repository("todoRepository")
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByCreatedBy(String createdBy);
}
