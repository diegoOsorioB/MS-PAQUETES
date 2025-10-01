package MS_Paquetes.paquetes.dto;

import jakarta.persistence.Entity;


public record ServicioResponse(
        Integer id,
        String nombreServicio,
        String descripcion,
        Double precio,
        Integer cantidad,
        String detallesAdicionales
) {
}
