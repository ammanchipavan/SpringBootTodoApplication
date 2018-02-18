package com.todo.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.todo.springboot.model.Todo;
import com.todo.springboot.service.TodoService;
import com.todo.springboot.util.CustomErrorType;

/**
 * 
 * Owner - Pavan Ammanchi
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	TodoService todoService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/todo/", method = RequestMethod.GET)
	public ResponseEntity<List<Todo>> listAllTodos() {
		List<Todo> todos = todoService.findAllTodos();
		if (todos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
	}

	// -------------------Retrieve Single Task------------------------------------------

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTodo(@PathVariable("id") long id) {
		logger.info("Fetching Task with id {}", id);
		Todo todo = todoService.findById(id);
		if (todo == null) {
			logger.error("Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Task with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	// -------------------Create a Task-------------------------------------------

	@RequestMapping(value = "/todo/", method = RequestMethod.POST)
	public ResponseEntity<?> createTodo(@RequestBody Todo todo, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Task : {}", todo);

		if (todoService.isTodoExist(todo)) {
			logger.error("Unable to create. A Task with name {} already exist", todo.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Task with name " + 
			todo.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		todoService.saveTodo(todo);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/todo/{id}").buildAndExpand(todo.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Task ------------------------------------------------

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
		logger.info("Updating Task with id {}", id);

		Todo todoTask = todoService.findById(id);

		if (todoTask == null) {
			logger.error("Unable to update. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		todoTask.setName(todo.getName());
		todoTask.setDescription(todo.getDescription());
		todoTask.setStatus(todo.getStatus());

		todoService.updateTodo(todoTask);
		return new ResponseEntity<Todo>(todoTask, HttpStatus.OK);
	}

	// ------------------- Delete a Task-----------------------------------------

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTodo(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Task with id {}", id);

		Todo todo = todoService.findById(id);
		if (todo == null) {
			logger.error("Unable to delete. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		todoService.deleteTodoById(id);
		return new ResponseEntity<Todo>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/todo/", method = RequestMethod.DELETE)
	public ResponseEntity<Todo> deleteAllTodos() {
		logger.info("Deleting All Users");

		todoService.deleteAllTodos();
		return new ResponseEntity<Todo>(HttpStatus.NO_CONTENT);
	}

}