package com.everis.latam.BKDProveedoresFlama.service.implement;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.everis.latam.BKDProveedoresFlama.URLs.URLs;
import com.everis.latam.BKDProveedoresFlama.dto.AreaDto;
import com.everis.latam.BKDProveedoresFlama.dto.CentroCostoDto;
import com.everis.latam.BKDProveedoresFlama.dto.EstadoSolicitudDto;
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResolucionResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;
import com.everis.latam.BKDProveedoresFlama.dto.WaiverDto;
import com.everis.latam.BKDProveedoresFlama.service.BackEndService;

@Service
public class BackEndServiceImplement implements BackEndService {

	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	RestTemplate rest;

	@Override
	public SolicitudDto obtenerarea(ProveedorDto prov, AreaDto area, SolicitanteDto solicitante, SolicitudDto solicitud,
			String descripcion) {

		ProveedorDto proveedorBD = new ProveedorDto();
		AreaDto areaBD = new AreaDto();
		SolicitanteDto solicitanteBD = new SolicitanteDto();
		SolicitudDto solicitudBD = new SolicitudDto();
		SolicitudDto solicitudArmar = new SolicitudDto();
		String Descripcion = descripcion;

		// mapeo
		ProveedorDto proveedorRecibido = prov;
		AreaDto areaRecibida = area;
		SolicitanteDto solicitanteRecibido = solicitante;
		SolicitudDto solicitudRecibida = solicitud;

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<ProveedorDto> provEntity = new HttpEntity<>(proveedorRecibido, headers);
		proveedorBD = rest.exchange(URLs.ProveedorInsert, HttpMethod.POST, provEntity, ProveedorDto.class).getBody();

		HttpEntity<AreaDto> areaEntity = new HttpEntity<>(areaRecibida, headers);
		areaBD = rest.exchange(URLs.AreaInsert, HttpMethod.POST, areaEntity, AreaDto.class).getBody();

		HttpEntity<SolicitanteDto> solicitanteEntity = new HttpEntity<>(solicitanteRecibido, headers);
		solicitanteBD = rest.exchange(URLs.SolicitanteInsert, HttpMethod.POST, solicitanteEntity, SolicitanteDto.class)
				.getBody();

		solicitudArmar = armarSolicitud(proveedorBD, areaBD, solicitanteBD, descripcion, solicitud);
		System.out.println("Solicitud a subir (falta revisar monto):" + solicitudArmar);

		HttpEntity<SolicitudDto> solicitudEntity = new HttpEntity<>(solicitudArmar, headers);
		solicitudBD = rest.exchange(URLs.SolicitudInsert, HttpMethod.POST, solicitudEntity, SolicitudDto.class)
				.getBody();
		System.out.println("Solicitud a responder: " + solicitudBD);
		// aqui debe consultar el monto de la solicitud con el centro asignado
		// si esta en rango se almacena y se envia a finanzas (imprimir por pantalla por
		// el momento)
		// si armar un resolucion response con datos x para enviarlo de vuelta al
		// servicio(el que envio elJson original)
		// si no esta en rango revisar la prioridad de la solicitud las cuales son:
		// 1-baja, 2-media- 3-alta
		// revisar la prioridar si es alta hacer consulta a waiver por idaprbador
		// si ambos estan en orden enviar a finanzas
		// si no modificar estado a solicitud antes de almacenar en bd

		// debe retornar un response (resolucionResponseDto) para el servicio(api) que
		// envio el json original
		return solicitudBD;

	}

	@Override
	public boolean ConsultarMontoenRango(AreaDto AreaConsultaCentro, SolicitudDto solicitud) {
		CentroCostoDto centroConsultarMonto = new CentroCostoDto();

		try {
			centroConsultarMonto = rest
					.getForEntity(URLs.CentroSearch + AreaConsultaCentro.getCeco(), CentroCostoDto.class).getBody();
			// HttpEntity<SolicitanteDto> solicitanteEntity = new
			// HttpEntity<>(solicitanteRecibido,headers);
			System.out.println("CentroCostoobtenido: " + centroConsultarMonto);

			if (solicitud.getMonto() <= centroConsultarMonto.getMontoMaximo()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean ConsultarAprobador(SolicitanteDto solicitante) {
		WaiverDto waiver = new WaiverDto();
		boolean respuesta = false;
		try {
			waiver = rest.getForEntity(URLs.WaiverSearch + solicitante.getIdAprobador(), WaiverDto.class).getBody();
			if (waiver.getWaiver()) {
				respuesta = true;
				return respuesta;
			} else {
				return respuesta;
			}
		} catch (Exception e) {
			respuesta = false;
			return respuesta;
		}

	}
	

	@Override
	public ResponseDto RechazarSolicitud(SolicitudDto solicitudFinal) {
		SolicitudDto mod = solicitudFinal;
		ResolucionDto Aux = new ResolucionDto();
		ResolucionDto rechazo = new ResolucionDto();
		try {
		Aux = rest.getForEntity(URLs.ResolucionLast , ResolucionDto.class).getBody();
		rechazo.setIdResolucion(Aux.getIdResolucion()+1);
		System.out.println("Resolucion traida: " + Aux);
		rechazo.setFechaResolucion( new Date());
		rechazo.setComentario("Solicitud rechazada por no cumplir con los requisitos");
		rechazo.setMontoTotal(0);
		rechazo.setSolicitudId(solicitudFinal.getSolicitudId());
		}catch (Exception e) {
			rechazo.setIdResolucion(1);
			rechazo.setMontoTotal(0);
			rechazo.setSolicitudId(solicitudFinal.getSolicitudId());
			rechazo.setFechaResolucion(new Date());
			rechazo.setComentario("Solicitud rechazada por no cumplir con los requisitos");
		}
		
		HttpEntity<ResolucionDto> resolucionEntity = new HttpEntity<>(rechazo, headers);
		ResolucionDto BD = rest.exchange(URLs.ResolucionInsert, HttpMethod.POST, resolucionEntity, ResolucionDto.class).getBody();
		
		HttpEntity<SolicitudDto> solicitudEntity = new HttpEntity<>(mod ,headers);
		SolicitudDto Mod = rest.exchange(URLs.SolicitudUpdate + mod.getSolicitudId()+ "&nuevoEstado=" + 3, HttpMethod.POST, solicitudEntity, SolicitudDto.class ).getBody();
		
		EstadoSolicitudDto estado = rest.getForEntity(URLs.EstadoSolicitudSearch + 3, EstadoSolicitudDto.class).getBody();
		
		ResponseDto respuesta = new ResponseDto();
		respuesta = ArmarRespuestaAdmin(estado, BD);
		//consulta a la bd por el ultimo registro
		//armar resolucion con id ultimo registro + 1
		// TODO Auto-generated method stub
		return respuesta;
	}

	private ResponseDto ArmarRespuestaAdmin(EstadoSolicitudDto estado, ResolucionDto BD) {
		ResponseDto respuesta = new ResponseDto();
		ResolucionResponseDto res= new ResolucionResponseDto();
		
		res.setIdResolucion(BD.getIdResolucion());
		res.setComentario(BD.getComentario());
		res.setMontoTotal(BD.getMontoTotal());
		respuesta.setStatus(estado.getEstado());
		respuesta.setResolucion(res);
		
		return respuesta;
	}

	private SolicitudDto armarSolicitud(ProveedorDto proveedorBD, AreaDto areaBD, SolicitanteDto solicitanteBD,
			String descripcion, SolicitudDto solicitud) {
		CentroCostoDto centroBuscar = new CentroCostoDto();

		centroBuscar = rest.getForEntity(URLs.CentroSearch + areaBD.getCeco(), CentroCostoDto.class).getBody();
		System.out.println("CENTRO: " + centroBuscar);
		// obtiene el centro acorde al area

		SolicitudDto respuesta = new SolicitudDto();

		respuesta = solicitud;
		respuesta.setAreaId(areaBD.getAreaID());
		// se inicializa en 1 por defecto ya que asi manda automaticamente como que la
		// solicitud fue recibida
		respuesta.setEstadoSolicitudId(1);
		respuesta.setProveedorIdRegistro(proveedorBD.getProveedorIdRegistro());
		respuesta.setSolicitanteId(solicitanteBD.getSolicitanteId());
		respuesta.setDescripcion(descripcion);
		respuesta.setCentroId(centroBuscar.getCentroId());

		// TODO Auto-generated method stub
		return respuesta;
	}


}
