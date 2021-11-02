package com.luizalabs.agenda.domain.entity;

import java.util.Arrays;

import com.luizalabs.agenda.exception.TipoNotFoundException;

public enum Tipo {
	WHATSAPP(0), SMS(1), EMAIL(2), PUSH(3);

	private final int value;

	Tipo(final int newValue) {
         value = newValue;
     }

	public int getValue() {
		return value;
	}

	public static Tipo find(int tipo) {
		return Arrays.asList(Tipo.values()).stream().filter(tipoMensagem -> tipoMensagem.getValue() == tipo).findAny().orElseThrow(() -> new TipoNotFoundException(tipo));
	}
}
