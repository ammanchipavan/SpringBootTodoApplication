package com.todo.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.springboot.model.Todo;

/**
 * 
 * User - Pavan Ammanchi
 */

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findByName(String name);
}
