package MS_PAQUETES.paquetes.exception;

public class PaqueteException {


    public static class PaqueteNotFound extends RuntimeException {
        public PaqueteNotFound(Integer id) {
            super("Paquete no encontrado: " + id);
        }
    }


    public static class DetalleNotFound extends RuntimeException {
        public DetalleNotFound(Integer id) {
            super("Detalle de paquete no encontrado: " + id);
        }
    }


    public static class ServicioNotFound extends RuntimeException {
        public ServicioNotFound(Integer id) {
            super("Servicio no encontrado: " + id);
        }
    }


    public static class DataBaseException extends RuntimeException {
        public DataBaseException(String mensaje) {
            super("Error en la base de datos: " + mensaje);
        }
    }

    public static class InvalidDataException extends RuntimeException {
        public InvalidDataException(String mensaje) {
            super("Datos inválidos: " + mensaje);
        }
    }

    public static class AuthException extends RuntimeException {
        public AuthException(String mensaje) {
            super("Acceso no autorizado: " + mensaje);
        }
    }
}
