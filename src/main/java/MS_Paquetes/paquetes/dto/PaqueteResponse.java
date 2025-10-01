package MS_Paquetes.paquetes.dto;

import java.math.BigDecimal;
import java.util.List;

public record PaqueteResponse(
        Integer id,
        String nombrePaquete,
        String descripcion,
        String duracion,
        Double precioPaquete,
        List<SeccionResponse> secciones
) {}
