package com.elineuton.bemtevi.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.elineuton.bemtevi.api.model.Aluno;
import com.elineuton.bemtevi.api.repositories.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository repositorio;
	
	
	public List<Aluno> listar(){
		return repositorio.findAll();
	}
	
	public Aluno consultarPorId(Long id) {
		Aluno aluno = repositorio.findById(id).orElse(null);
		return aluno;
	}
	
	public Aluno criar(Aluno aluno) {
		Aluno alunoSalvo = repositorio.save(aluno);
		return alunoSalvo;
	}
	
	public Aluno atualizar(Aluno obj, Long id) {
		Aluno objSalvo = repositorio.findById(id).get();
		
		if(objSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(obj, objSalvo, "id");
		return repositorio.save(objSalvo);
	}
	 
	public void remover(Long id) {
		repositorio.deleteById(id);
	}
	
	public Page<Aluno> buscarPagina(Integer pagina, Integer tamanho, String ordem, String direcao){
		PageRequest pageable = PageRequest.of(pagina, tamanho, Direction.valueOf(direcao), ordem);
		return repositorio.findAll(pageable);
	}

}
