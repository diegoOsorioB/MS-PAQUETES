package MS_PAQUETES.paquetes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExcepcion {

    @ExceptionHandler(PaqueteException.PaqueteNotFound.class)
    public ResponseEntity<Map<String, Object>> handlePaqueteNotFound(PaqueteException.PaqueteNotFound ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaqueteException.DetalleNotFound.class)
    public ResponseEntity<Map<String, Object>> handleDetalleNotFound(PaqueteException.DetalleNotFound ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaqueteException.ServicioNotFound.class)
    public ResponseEntity<Map<String, Object>> handleServicioNotFound(PaqueteException.ServicioNotFound ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaqueteException.DataBaseException.class)
    public ResponseEntity<Map<String, Object>> handleDataBase(PaqueteException.DataBaseException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(PaqueteException.InvalidDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidData(PaqueteException.InvalidDataException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PaqueteException.AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(PaqueteException.AuthException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtrosErrores(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado: " + ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("service", "MS-Paquetes");
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", mensaje);
        return new ResponseEntity<>(body, status);
    }
}
