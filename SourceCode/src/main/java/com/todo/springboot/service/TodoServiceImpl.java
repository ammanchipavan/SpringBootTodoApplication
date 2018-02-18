package com.todo.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.springboot.model.Todo;
import com.todo.springboot.repositories.TodoRepository;

/**
 * 
 * User - Pavan Ammanchi
 */


@Service("todoService")
@Transactional
public class TodoServiceImpl implements TodoService{

	@Autowired
	private TodoRepository todoRepository;

	public Todo findById(Long id) {
		return todoRepository.findOne(id);
	}

	public Todo findByName(String name) {
		return todoRepository.findByName(name);
	}

	public void saveTodo(Todo todo) {
		todoRepository.save(todo);
	}

	public void updateTodo(Todo todo){
		saveTodo(todo);
	}

	public void deleteTodoById(Long id){
		todoRepository.delete(id);
	}

	public void deleteAllTodos(){
		todoRepository.deleteAll();
	}

	public List<Todo> findAllTodos(){
		return todoRepository.findAll();
	}

	public boolean isTodoExist(Todo todo) {
		return findByName(todo.getName()) != null;
	}

}
