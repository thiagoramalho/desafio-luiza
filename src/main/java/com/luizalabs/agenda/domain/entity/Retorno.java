package com.luizalabs.agenda.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Retorno {
	
	private String message;
	private Integer status;
	
}