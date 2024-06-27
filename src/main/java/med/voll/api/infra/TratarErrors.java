package med.voll.api.infra;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErrors {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> notFound() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> badRequest(MethodArgumentNotValidException ex) {
		List<ErrorValidation> errorValidation = ex.getFieldErrors().stream().map(ErrorValidation::new).toList();
		
		return ResponseEntity.badRequest().body(errorValidation);
	}
}
