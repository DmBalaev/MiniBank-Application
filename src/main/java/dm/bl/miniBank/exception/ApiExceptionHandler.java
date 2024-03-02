package dm.bl.miniBank.exception;

import dm.bl.miniBank.payload.ResponseApi;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseApi> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Method argument not valid", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseApi> handleEntityNotFound(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseApi> handleDuplicateException(DuplicateException e) {
        log.error("Duplicate found", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseApi(e.getMessage()));
    }

    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseApi> handleInsufficientAmountException(InsufficientAmountException e) {
        log.error("Insufficient amount", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseApi> handleResourceNotFoundException(ResourceNotFound e) {
        log.error("Resource not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage()));
    }
}