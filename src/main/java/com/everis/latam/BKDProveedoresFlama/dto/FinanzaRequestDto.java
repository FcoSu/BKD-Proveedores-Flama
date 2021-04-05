package com.everis.latam.BKDProveedoresFlama.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FinanzaRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	String idProveedor;
	String nombreArea;
	String responsable;
	String email;
	String ceco;
	String idAprovador;
	int numero;
	int monto;
	float iva;
	int prioridad;
	int idSolicitud;
	

}
