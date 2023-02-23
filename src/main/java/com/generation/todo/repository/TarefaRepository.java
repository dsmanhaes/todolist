package com.generation.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.todo.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
