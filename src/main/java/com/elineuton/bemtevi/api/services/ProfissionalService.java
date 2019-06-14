package com.elineuton.bemtevi.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.elineuton.bemtevi.api.model.Profissional;
import com.elineuton.bemtevi.api.repositories.ProfissionalRepository;

@Service
public class ProfissionalService {
	
	@Autowired
	private ProfissionalRepository repositorio;
	
	
	public List<Profissional> listar(){
		return repositorio.findAll();
	}
	
	public Profissional consultarPorId(Long id) {
		Profissional obj = repositorio.findById(id).orElse(null);
		return obj;
	}
	
	public Profissional criar(Profissional obj) {
		Profissional objSalvo = repositorio.save(obj);
		return objSalvo;
	}
	
	public Profissional atualizar(Profissional obj, Long id) {
		Profissional objSalvo = repositorio.findById(id).get();
		
		if(objSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(obj, objSalvo, "id");
		return repositorio.save(objSalvo);
	}
	 
	public void remover(Long id) {
		repositorio.deleteById(id);
	}
	
	public Page<Profissional> buscarPagina(Integer pagina, Integer tamanho, String ordem, String direcao){
		PageRequest pageable = PageRequest.of(pagina, tamanho, Direction.valueOf(direcao), ordem);
		return repositorio.findAll(pageable);
	}
	
/*	public Profissional fromDTO(ProfissionalDTO objDto) {
		return null; //TODO Implementar o método de conversão de uma ProfissionalDTO em Profissional
	}
*/
}
