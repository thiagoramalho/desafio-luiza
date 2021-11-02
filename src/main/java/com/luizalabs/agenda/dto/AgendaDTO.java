package com.luizalabs.agenda.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalabs.agenda.domain.entity.Tipo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {

	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataEnvio;

	@NonNull
	private String mensagem;

	@NonNull
	private String destinatario;

	@NonNull
	private Tipo tipo;
}
