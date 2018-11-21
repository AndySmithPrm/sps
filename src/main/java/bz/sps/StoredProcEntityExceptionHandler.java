package bz.sps;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * перехватчик ошибок валидации,
 * оформляет их в более культурном виде
 */
@ControllerAdvice
@RestController
public class StoredProcEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<StoredProcValidError> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> new StoredProcValidError(fe.getField(), fe.getRejectedValue())).collect(Collectors.toList());
		StoredProcResult result = new StoredProcResult(false, "ERROR_ARGUMENT_NOT_VALID", errors);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
}