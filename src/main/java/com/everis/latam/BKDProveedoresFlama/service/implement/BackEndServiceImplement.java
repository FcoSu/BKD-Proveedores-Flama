package com.everis.latam.BKDProveedoresFlama.service.implement;

import java.net.URI;
import java.util.Arrays;

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
import com.everis.latam.BKDProveedoresFlama.dto.ProveedorDto;
import com.everis.latam.BKDProveedoresFlama.dto.ResponseAdminDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitanteDto;
import com.everis.latam.BKDProveedoresFlama.dto.SolicitudDto;
import com.everis.latam.BKDProveedoresFlama.service.BackEndService;


@Service
public class BackEndServiceImplement implements BackEndService {
	
	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	RestTemplate rest;

	@Override
	public ResponseAdminDto obtenerarea(ProveedorDto prov, AreaDto area, SolicitanteDto solicitante, SolicitudDto solicitud,
			String descripcion) {
		
		
		ProveedorDto proveedorBD = new ProveedorDto();
		AreaDto areaBD = new AreaDto();
		SolicitanteDto solicitanteBD = new SolicitanteDto();
		SolicitudDto solicitudBD = new SolicitudDto();
		SolicitudDto solicitudArmar = new SolicitudDto();
		String Descripcion = descripcion;
		
		//mapeo
		ProveedorDto proveedorRecibido = prov;
		AreaDto areaRecibida = area;
		SolicitanteDto solicitanteRecibido = solicitante;
		SolicitudDto solicitudRecibida = solicitud;
		
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<ProveedorDto> provEntity = new HttpEntity<>(proveedorRecibido, headers);
		proveedorBD = rest.exchange(URLs.ProveedorInsert, HttpMethod.POST, provEntity,ProveedorDto.class).getBody();
		
		HttpEntity<AreaDto> areaEntity = new HttpEntity<>(areaRecibida,headers);
		areaBD = rest.exchange(URLs.AreaInsert, HttpMethod.POST, areaEntity, AreaDto.class).getBody();
		
		HttpEntity<SolicitanteDto> solicitanteEntity = new HttpEntity<>(solicitanteRecibido,headers);
		solicitanteBD = rest.exchange(URLs.SolicitanteInsert,HttpMethod.POST, solicitanteEntity, SolicitanteDto.class).getBody();
		
		
		
		solicitudArmar = armarSolicitud(proveedorBD, areaBD,solicitanteBD,descripcion,solicitud);
		System.out.println("Solicitud a subir (falta revisar monto):" + solicitudArmar);
		
		//aqui debe consultar el monto de la solicitud con el centro asignado
		//si esta en rango se almacena y se envia a finanzas (imprimir por pantalla por el momento)
		//si armar un resolucion response con datos x para enviarlo de vuelta al servicio(el que envio elJson original)
		//si no esta en rango revisar la prioridad de la solicitud las cuales son:
		// 1-baja, 2-media- 3-alta
		//revisar la prioridar si es alta hacer consulta a waiver por idaprbador
		//si ambos estan en orden enviar a finanzas
		//si no modificar estado a solicitud antes de almacenar en bd
		
		
		
		//debe retornar un response (resolucionResponseDto) para el servicio(api) que envio el json original
		return null;
		
	}

	private SolicitudDto armarSolicitud(ProveedorDto proveedorBD, AreaDto areaBD, SolicitanteDto solicitanteBD,
			String descripcion,SolicitudDto solicitud) {
		CentroCostoDto centroBuscar = new CentroCostoDto();
		
		
		centroBuscar= rest.getForEntity(URLs.CentroSearch + areaBD.getCeco(), CentroCostoDto.class).getBody();
		System.out.println("CENTRO: "+ centroBuscar);
		//obtiene el centro acorde al area
		
		SolicitudDto respuesta = new SolicitudDto();
		
		respuesta = solicitud;
		respuesta.setAreaId(areaBD.getAreaID());
		//se inicializa en 1 por defecto ya que asi manda automaticamente como que la solicitud fue recibida
		respuesta.setEstadoSolicitudId(1);
		respuesta.setProveedorIdRegistro(proveedorBD.getProveedorIdRegistro());
		respuesta.setSolicitanteId(solicitanteBD.getSolicitanteId());
		respuesta.setDescripcion(descripcion);
		respuesta.setCentroId(centroBuscar.getCentroId());
		
		
		// TODO Auto-generated method stub
		return respuesta;
	}

}
