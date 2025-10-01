package MS_Paquetes.paquetes.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExcepcionesGlobales {

    @ExceptionHandler(Excepciones.PaquetesNotFoundException.class)
    public ResponseEntity<?> manejarPaquetes(Excepciones.PaquetesNotFoundException paquetesNotFoundException)
    {
        return buildResponse(HttpStatus.NOT_FOUND,paquetesNotFoundException.getMessage());
    }

    @ExceptionHandler({Excepciones.DatabaseException.class, DataAccessException.class})
    public ResponseEntity<?> manejarErrorBD(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos: " + ex.getMessage());
    }

    @ExceptionHandler(Excepciones.AuthException.class)
    public ResponseEntity<?> manejarAuth(Excepciones.AuthException  ex )
    {
        return buildResponse(HttpStatus.UNAUTHORIZED,ex.getMessage());
    }

    @ExceptionHandler(Excepciones.ValidationException.class)
    public ResponseEntity<?> manejarValidacion(Excepciones.ValidationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private  ResponseEntity<?> buildResponse(HttpStatus status, String message)
    {
        Map<String,Object> response = new HashMap<>();
        response.put("Origen","MS-Clientes");
        response.put("timestamp", LocalDateTime.now());
        response.put("status",status);
        response.put("error",status.getReasonPhrase());
        response.put("message",message);
        return new ResponseEntity<>(response,status);
    }
}
