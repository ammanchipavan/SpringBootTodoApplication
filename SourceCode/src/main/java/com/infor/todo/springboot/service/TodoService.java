package com.infor.todo.springboot.service;


import java.util.List;

import com.infor.todo.springboot.model.Todo;

/**
 * 
 * User - Pavan Ammanchi
 */

public interface TodoService {
	
	Todo findById(Long id);

	Todo findByName(String name);

	void saveTodo(Todo todo);

	void updateTodo(Todo todo);

	void deleteTodoById(Long id);

	void deleteAllTodos();

	List<Todo> findAllTodos();

	boolean isTodoExist(Todo todo);
}