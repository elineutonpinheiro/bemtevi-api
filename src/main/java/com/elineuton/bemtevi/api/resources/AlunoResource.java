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

import com.elineuton.bemtevi.api.model.Aluno;
import com.elineuton.bemtevi.api.services.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {
	
	@Autowired
	private AlunoService service;
	
	@GetMapping
	public ResponseEntity<List<Aluno>> listar(){
		List<Aluno> lista = service.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> consultaPorId(@PathVariable Long id) {
		Aluno obj = service.consultarPorId(id);
		return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Aluno> criar(@Valid @RequestBody Aluno obj, HttpServletResponse response) {
		Aluno objSalvo = service.criar(obj);
		
		//Mapear o recurso -> aluno+id
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(objSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(objSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aluno> atualizar(@Valid @RequestBody Aluno obj, @PathVariable Long id) {
		Aluno objSalvo = service.atualizar(obj, id);
		return ResponseEntity.ok(objSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/page") //TODO Implementar o AlunoDTO posteriormente
	public ResponseEntity<Page<Aluno>> listarAlunosPage(
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="size", defaultValue="24") Integer tamanho, 
			@RequestParam(value="orderBy", defaultValue="nome") String ordem, 
			@RequestParam(value="direction", defaultValue="ASC") String direcao) {
		Page<Aluno> lista = service.buscarPagina(pagina, tamanho, ordem, direcao);
		//Page<AlunoDTO> listDto = lista.map(obj -> new AlunoDTO(obj));
		return ResponseEntity.ok(lista);	
	}

}
