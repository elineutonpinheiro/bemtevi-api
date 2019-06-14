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

import com.elineuton.bemtevi.api.model.Unidade;
import com.elineuton.bemtevi.api.services.UnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeResource {
	
	@Autowired
	private UnidadeService service;
	
	@GetMapping
	public ResponseEntity<List<Unidade>> listar(){
		List<Unidade> lista = service.listar();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Unidade> consultaPorId(@PathVariable Long id) {
		Unidade obj = service.consultarPorId(id);
		return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Unidade> criar(@Valid @RequestBody Unidade obj, HttpServletResponse response) {
		Unidade objSalvo = service.criar(obj);
		
		//Mapear o recurso -> unidade+id
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(objSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(objSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Unidade> atualizar(@Valid @RequestBody Unidade obj, @PathVariable Long id) {
		Unidade objSalvo = service.atualizar(obj, id);
		return ResponseEntity.ok(objSalvo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/page") //TODO Implementar o UnidadeDTO posteriormente
	public ResponseEntity<Page<Unidade>> listarUnidadesPage(
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="size", defaultValue="24") Integer tamanho, 
			@RequestParam(value="orderBy", defaultValue="nome") String ordem, 
			@RequestParam(value="direction", defaultValue="ASC") String direcao) {
		Page<Unidade> lista = service.buscarPagina(pagina, tamanho, ordem, direcao);
		//Page<UnidadeDTO> listDto = lista.map(obj -> new UnidadeDTO(obj));
		return ResponseEntity.ok(lista);	
	}

}
