package com.luizalabs.agenda.exception;

public class TipoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -29073604402550750L;

	public TipoNotFoundException(Integer fromString) {
		super("Tipo de mensagem não encontrada");
	}
}
