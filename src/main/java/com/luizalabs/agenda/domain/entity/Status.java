package com.luizalabs.agenda.domain.entity;

import java.util.Arrays;

import com.luizalabs.agenda.exception.StatusNotFoundException;

public enum Status {
	PENDENTE(0), ENVIADO(1), CANCELADO(2);

	private Integer status;

	private Status(Integer status) {
		this.status = status;
	}

	private Integer getStatus() {
		return status;
	}

	public static Status find(Integer status) {
		return Arrays.asList(Status.values()).stream().filter(statusMensagem -> statusMensagem.getStatus() == status).findAny().orElseThrow(() -> new StatusNotFoundException(status));
	}
	
}
