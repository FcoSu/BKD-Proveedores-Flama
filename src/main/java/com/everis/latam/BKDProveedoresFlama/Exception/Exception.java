package com.everis.latam.BKDProveedoresFlama.Exception;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Exception {
	private final String msg;
	private final HttpStatus httpStatus;
	private final ZonedDateTime time;
}
