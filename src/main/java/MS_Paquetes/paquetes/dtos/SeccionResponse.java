package MS_PAQUETES.paquetes.dtos;

import java.util.List;

public record SeccionResponse(
        Integer id,
        String titulo,
        String descripcion,
        List<ServicioResponse> servicios
) {
}
