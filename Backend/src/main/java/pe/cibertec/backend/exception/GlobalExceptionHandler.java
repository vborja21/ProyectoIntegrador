package pe.cibertec.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.cibertec.backend.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(EntityNotFoundException ex){
        var status = HttpStatus.NOT_FOUND;
        var errorResponse=new ErrorResponseDto(status,
                ex.getMessage(), ex.toString());
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(CustomDuplicateKeyException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomDuplicateKeyException(CustomDuplicateKeyException ex) {
        var status = HttpStatus.BAD_REQUEST;
        var errorResponse = new ErrorResponseDto(status, ex.getMessage(), ex.toString());
        return new ResponseEntity<>(errorResponse, status);
    }
}
