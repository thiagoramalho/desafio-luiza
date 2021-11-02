package com.luizalabs.desafioLuizaLabs;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.luizalabs.agenda.DesafioLuizaApplication;
import com.luizalabs.agenda.controller.AgendaController;
import com.luizalabs.agenda.dao.AgendaRepository;
import com.luizalabs.agenda.domain.entity.Agenda;
import com.luizalabs.agenda.domain.entity.Retorno;
import com.luizalabs.agenda.domain.entity.Status;
import com.luizalabs.agenda.domain.entity.Tipo;
import com.luizalabs.agenda.dto.AgendaDTO;

@SpringBootTest(classes = DesafioLuizaApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class DesafioLuizaLabsApplicationTests {

	@Autowired
	private AgendaController agendaController;

	@Autowired
	private AgendaRepository agendaRepository;

	@Test
	@Order(1)
	void criarAgendamento() throws Exception {

		AgendaDTO agendaDTO = new AgendaDTO(LocalDateTime.now(), "teste", "81993811377", Tipo.SMS);
		ResponseEntity<Object> response = agendaController.agendar(agendaDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@Order(2)
	void criarAgendamentoEmailInvalido() throws Exception {

		AgendaDTO agendaDTO = new AgendaDTO(LocalDateTime.now(), "teste", "81993811377", Tipo.EMAIL);
		ResponseEntity<Object> response = agendaController.agendar(agendaDTO);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@Order(3)
	void consultarStatus() throws Exception {
		Agenda objeto = agendaRepository.findAll().get(0);
		ResponseEntity<Object> response = agendaController.getStatusById(objeto.getId().toString());
		Retorno retorno = (Retorno) response.getBody();
		Assertions.assertThat(retorno.getMessage()).isEqualTo(Status.PENDENTE.toString());
	}

	@Test
	@Order(4)
	void cancelarAgendamento() throws Exception {
		Agenda objeto = agendaRepository.findAll().get(0);
		ResponseEntity<Object> response = agendaController.cancelar(objeto.getId().toString());
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
