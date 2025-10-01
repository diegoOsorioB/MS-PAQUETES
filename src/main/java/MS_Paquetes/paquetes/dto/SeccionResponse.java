package MS_Paquetes.paquetes.dto;

import jakarta.persistence.Entity;

import java.util.List;

public record SeccionResponse(
        Integer id,
        String titulo,
        String descripcion,
        List<ServicioResponse> servicios
) {
}
