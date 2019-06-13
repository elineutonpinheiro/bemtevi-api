package com.elineuton.bemtevi.api.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Categoria {
	
	ALIMENTACAO("Alimentação"),
	SAUDE("Saúde"),
	SONO("Sono"),
	HIGIENE("Higiene"),
	INTERACAO("Interação"),
	COMPORTAMENTO("Comportamento");
	
	private String descricao;
	
	private Categoria (String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
	
	public static Categoria toEnum(String descricao) {
		if(descricao == null) {
			return null;
		}
		
		for(Categoria p : Categoria.values()) {
			if(descricao.equals(p.getDescricao())) {
				return p;
			}
		}
		
		throw new IllegalArgumentException("Descricao inválida: " + descricao);
	}

}
