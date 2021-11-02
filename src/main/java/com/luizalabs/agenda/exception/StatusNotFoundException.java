package com.luizalabs.agenda.exception;

public class StatusNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2729521558170495088L;

	public StatusNotFoundException(Integer fromString) {
		super("Status não encontrado");
	}
}
