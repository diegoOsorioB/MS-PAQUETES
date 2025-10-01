package MS_Paquetes.paquetes.exception;

public class Excepciones {

    public static class PaquetesNotFoundException extends RuntimeException{

        public PaquetesNotFoundException(int id)
        {
            super("No se encontro el paquete "+id);
        }
        public PaquetesNotFoundException(String  nombre){
            super("No se encontro el paquete ");
        }
    }

    public static class ServicioNotFoundException extends RuntimeException{
        public ServicioNotFoundException()
        {
            super("Servicios no encontrados");
        }
    }

    public static class DatabaseException extends RuntimeException {
        public DatabaseException(String mensaje) {
            super(mensaje);
        }
    }

    public static class AuthException extends RuntimeException {
        public AuthException(String msg) {
            super(msg);
        }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String mensaje) {
            super(mensaje);
        }
    }
}
