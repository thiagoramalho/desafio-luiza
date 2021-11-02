package com.luizalabs.agenda.exception;

public class AgendaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1501600074435879651L;

	public AgendaNotFoundException() {
		super("Agendamento não encontrado!");
	}
}
