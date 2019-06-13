package com.elineuton.bemtevi.api.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sexo {
	
	FEMININO("F"),
	MASCULINO("M");
	
	private String descricao;
	
	private Sexo (String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
	
	public static Sexo toEnum(String descricao) {
		if(descricao == null) {
			return null;
		}
		
		for(Sexo p : Sexo.values()) {
			if(descricao.equals(p.getDescricao())) {
				return p;
			}
		}
		
		throw new IllegalArgumentException("Descricao inválida: " + descricao);
	}

}
