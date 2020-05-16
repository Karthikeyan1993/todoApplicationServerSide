package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.todo.entity.Todo;

@Repository("todoRepository")
public interface TodoRepository extends JpaRepository<Todo,Long> {

}
