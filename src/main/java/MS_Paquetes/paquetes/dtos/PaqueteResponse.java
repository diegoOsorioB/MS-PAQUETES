package MS_PAQUETES.paquetes.dtos;

import java.util.List;

public record PaqueteResponse(
        Integer  id,
        String nombrePaquete,
        String descripcion,
        String duracion,
        Double precioPaquete,
        List<SeccionResponse> secciones
) {
}
