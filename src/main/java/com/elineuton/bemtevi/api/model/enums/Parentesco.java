package com.elineuton.bemtevi.api.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Parentesco {
	
	PAI("Pai"), 
	MAE("Mãe"), 
	IRMAO("Irmão"), 
	IRMA("Irmã"), 
	TIO("Tio"), 
	TIA("Tia"),
    PRIMO("Primo"), 
    PRIMA("Prima"), 
    AVO1("Avô"), 
    AVO2("Avó"),
    OUTRO("Outro");
	
	private String descricao;
	
	private Parentesco (String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
	
	public static Parentesco toEnum(String descricao) {
		if(descricao == null) {
			return null;
		}
		
		for(Parentesco p : Parentesco.values()) {
			if(descricao.equals(p.getDescricao())) {
				return p;
			}
		}
		
		throw new IllegalArgumentException("Descricao inválida: " + descricao);
	}

}
