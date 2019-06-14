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

import com.elineuton.bemtevi.api.model.Profissional;
import com.elineuton.bemtevi.api.services.ProfissionalService;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalResource {
	
	@Autowired
	private ProfissionalService service;
	
	@GetMapping
	public ResponseEntity<List<Profissional>> listar(){
		List<Profissional> lista = service.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Profissional> consultaPorId(@PathVariable Long id) {
		Profissional obj = service.consultarPorId(id);
		return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Profissional> criar(@Valid @RequestBody Profissional obj, HttpServletResponse response) {
		Profissional objSalvo = service.criar(obj);
		
		//Mapear o recurso -> profissional+id
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(objSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(objSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Profissional> atualizar(@Valid @RequestBody Profissional obj, @PathVariable Long id) {
		Profissional objSalvo = service.atualizar(obj, id);
		return ResponseEntity.ok(objSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/page") //TODO Implementar o ProfissionalDTO posteriormente
	public ResponseEntity<Page<Profissional>> listarProfissionalsPage(
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="size", defaultValue="24") Integer tamanho, 
			@RequestParam(value="orderBy", defaultValue="nome") String ordem, 
			@RequestParam(value="direction", defaultValue="ASC") String direcao) {
		Page<Profissional> lista = service.buscarPagina(pagina, tamanho, ordem, direcao);
		//Page<ProfissionalDTO> listDto = lista.map(obj -> new ProfissionalDTO(obj));
		return ResponseEntity.ok(lista);	
	}

}
