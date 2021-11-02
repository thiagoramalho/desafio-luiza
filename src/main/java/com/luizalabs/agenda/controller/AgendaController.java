package com.luizalabs.agenda.controller;

import java.util.UUID;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizalabs.agenda.dao.AgendaRepository;
import com.luizalabs.agenda.domain.entity.Agenda;
import com.luizalabs.agenda.domain.entity.Retorno;
import com.luizalabs.agenda.domain.entity.Status;
import com.luizalabs.agenda.domain.entity.Tipo;
import com.luizalabs.agenda.dto.AgendaDTO;
import com.luizalabs.agenda.exception.AgendaNotFoundException;

import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/agendamento")
public class AgendaController {

	@Autowired
	AgendaRepository repository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping(path = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Consulta o status do agendamento de envio de comunicação.", summary = "Consulta o status do agendamento")
	@ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Consulta realizada.", response = Retorno.class),
			@io.swagger.annotations.ApiResponse(code = 404, message = "O agendamento consultado não existe"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "A requisição não foi aceita. Geralmente por falta de um parâmetro requerido"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Os parâmetros foram validados, mas houve algum erro de negócio") })
	public ResponseEntity<Object> getStatusById(@PathVariable(value = "id") String id) {
		try {
			Agenda agenda = repository.findById(UUID.fromString(id)).orElseThrow(() -> new AgendaNotFoundException());
			return ResponseEntity.ok(new Retorno(agenda.getStatus().toString(), HttpStatus.OK.value()));
		} catch (AgendaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Retorno(e.getMessage(), HttpStatus.NOT_FOUND.value()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Retorno(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));

		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Realiza o agendamento do envio.", summary = "Agendamento de envio de comunicação")
	@ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 201, message = "Agendamento realizado.", response = Retorno.class),
			@io.swagger.annotations.ApiResponse(code = 404, message = "O recurso solicitado não existe", response = Retorno.class),
			@io.swagger.annotations.ApiResponse(code = 400, message = "A requisição não foi aceita. Geralmente por falta de um parâmetro requerido", response = Retorno.class),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Os parâmetros foram validados, mas houve algum erro de negócio", response = Retorno.class) })
	public ResponseEntity<Object> agendar(@RequestBody AgendaDTO agendamento) {
		try {

			if (agendamento.getTipo().equals(Tipo.EMAIL)) {
				InternetAddress emailAddr = new InternetAddress(agendamento.getDestinatario());
				emailAddr.validate();
			} else if (agendamento.getTipo().equals(Tipo.SMS) || agendamento.getTipo().equals(Tipo.WHATSAPP)) {
				Pattern p = Pattern.compile("^\\d{11}$");
				if (!p.matcher(agendamento.getDestinatario()).matches()) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body(new Retorno("Número inválido. Informe no formato DDXXXXXXXXX. Ex.:81993144688", HttpStatus.INTERNAL_SERVER_ERROR.value()));
				}
			}

			Agenda objeto = modelMapper.map(agendamento, Agenda.class);
			objeto.setStatus(Status.PENDENTE);
			Agenda agendamentoSalvo = repository.save(objeto);
			return ResponseEntity.status(HttpStatus.CREATED).body(new Retorno("Agendamento realizado com sucesso! ID: " +agendamentoSalvo.getId().toString(), HttpStatus.CREATED.value()));
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Retorno(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		} catch (AddressException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Retorno("Email inválido", HttpStatus.INTERNAL_SERVER_ERROR.value()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Retorno(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	@PutMapping("/cancelar/{id}")
	@Operation(description = "Cancelamento do agendamento de envio de comunicação.", summary = "Cancela o agendamento")
	@ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Cancelamento realizado.", response = Retorno.class),
			@io.swagger.annotations.ApiResponse(code = 404, message = "O agendamento consultado não existe"),
			@io.swagger.annotations.ApiResponse(code = 400, message = "A requisição não foi aceita. Geralmente por falta de um parâmetro requerido"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Os parâmetros foram validados, mas houve algum erro de negócio") })
	public ResponseEntity<Object> cancelar(@PathVariable(value = "id") String id) {
		try {
			Agenda agenda = repository.findById(UUID.fromString(id)).orElseThrow(() -> new AgendaNotFoundException());
			agenda.setStatus(Status.CANCELADO);
			repository.save(agenda);
			return ResponseEntity.ok(new Retorno("Cancelamento realizado com sucesso!", HttpStatus.OK.value()));
		} catch (AgendaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Retorno(e.getMessage(), HttpStatus.NOT_FOUND.value()));
		}
	}

}
