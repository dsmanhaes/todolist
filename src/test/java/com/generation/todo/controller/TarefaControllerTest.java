package com.generation.todo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.todo.model.Tarefa;
import com.generation.todo.repository.TarefaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TarefaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Test
	@DisplayName("Deve criar uma tarefa")
	public void deveCriarTarefa() throws Exception {
		
		Tarefa tarefa = new Tarefa(0L, "Tarefa 01", "Tarefa de número 1", "Fulano", LocalDate.now(), false);
		
		HttpEntity<Tarefa> corpoRequisicao = new HttpEntity<Tarefa>(tarefa);
		
		ResponseEntity<Tarefa> resposta = testRestTemplate.exchange("/tarefas", HttpMethod.POST, corpoRequisicao, Tarefa.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), resposta.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deve retornar uma tarefa pelo Id")
	public void deveRetornarPorId() {
		
		Tarefa buscaTarefa = tarefaRepository.save(new Tarefa(0L, "Tarefa 02", "Tarefa de número 2", "Beltrano", LocalDate.now(), false));
		
		ResponseEntity<Tarefa> resposta = testRestTemplate.exchange("/tarefas/" + buscaTarefa.getId(), HttpMethod.GET, null, Tarefa.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(buscaTarefa.getNome(), resposta.getBody().getNome());
		assertTrue(buscaTarefa.getId() != 0L);
	}
	
	// Listar todas as tarefas
	// Buscar uma tarefa pelo nome
	// Editar uma tarefa
	// Apagar uma tarefa
}
