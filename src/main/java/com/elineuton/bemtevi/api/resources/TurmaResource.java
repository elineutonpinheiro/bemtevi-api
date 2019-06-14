package com.elineuton.bemtevi.api.resources;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.elineuton.bemtevi.api.model.Turma;
import com.elineuton.bemtevi.api.services.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaResource {

	@Autowired
	private TurmaService service;
	
	@GetMapping
	public ResponseEntity<List<Turma>> listar(){
		List<Turma> lista = service.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Turma> consultaPorId(@PathVariable Long id) {
		Turma obj = service.consultarPorId(id);
		return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Turma> criar(@Valid @RequestBody Turma obj, HttpServletResponse response) {
		Turma objSalvo = service.criar(obj);
		
		//Mapear o recurso -> unidade+id
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(objSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(objSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Turma> atualizar(@Valid @RequestBody Turma obj, @PathVariable Long id) {
		Turma objSalvo = service.atualizar(obj, id);
		return ResponseEntity.ok(objSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/page") //TODO Implementar o TurmaDTO posteriormente
	public ResponseEntity<Page<Turma>> listarTurmasPage(
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="size", defaultValue="24") Integer tamanho, 
			@RequestParam(value="orderBy", defaultValue="descricao") String ordem, 
			@RequestParam(value="direction", defaultValue="ASC") String direcao) {
		Page<Turma> lista = service.buscarPagina(pagina, tamanho, ordem, direcao);
		return ResponseEntity.ok(lista);	
	}
	
}
