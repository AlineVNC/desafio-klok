package br.com.alinevieira.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	    
	@Autowired
	protected MessageSource messageSource;
	    
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleRuntimeException(NotFoundException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<Object> handleRuntimeException(BadRequestException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        
        return handleExceptionInternal(ex, "Operação inválida.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
