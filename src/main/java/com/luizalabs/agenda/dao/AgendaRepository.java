package com.luizalabs.agenda.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizalabs.agenda.domain.entity.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {

}
