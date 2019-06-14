package com.elineuton.bemtevi.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.elineuton.bemtevi.api.model.Responsavel;
import com.elineuton.bemtevi.api.repositories.ResponsavelRepository;

@Service
public class ResponsavelService {

	@Autowired
	private ResponsavelRepository repositorio;
	
	
	public List<Responsavel> listar(){
		return repositorio.findAll();
	}
	
	public Responsavel consultarPorId(String cpf) {
		Responsavel obj = repositorio.findById(cpf).orElse(null);
		return obj;
	}
	
	public Responsavel criar(Responsavel obj) {
		Responsavel objSalvo = repositorio.save(obj);
		return objSalvo;
	}
	
	public Responsavel atualizar(Responsavel obj, String cpf) {
		Responsavel objSalvo = repositorio.findById(cpf).get();
		
		if(objSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(obj, objSalvo, "id");
		return repositorio.save(objSalvo);
	}
	 
	public void remover(String cpf) {
		repositorio.deleteById(cpf);
	}
	
	public Page<Responsavel> buscarPagina(Integer pagina, Integer tamanho, String ordem, String direcao){
		PageRequest pageable = PageRequest.of(pagina, tamanho, Direction.valueOf(direcao), ordem);
		return repositorio.findAll(pageable);
	}
	
	
	//public Responsavel fromDTO(ResponsavelDTO objDto) {
		//return new Responsavel(objDto.getId(), objDto.getParentesco(),objDto.getNome()); 
		//TODO Implementar o método de conversão de uma ResponsavelDTO em Responsavel
	//}
	
}
