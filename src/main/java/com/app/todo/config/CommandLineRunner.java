package com.app.todo.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.todo.TodoApplication;
import com.app.todo.entity.Todo;
import com.app.todo.repository.TodoRepository;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TodoApplication.class);

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.debug("Inital Testing Data Loaded");
		Todo t1 = new Todo("Testing Name", "Testing", "High", "In Progress", new Date());
		todoRepository.save(t1);
	}

}