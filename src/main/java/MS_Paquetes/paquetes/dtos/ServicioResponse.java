package MS_PAQUETES.paquetes.dtos;

public record ServicioResponse(
        Integer id,
        String nombrServicio,
        String descripcion,
        Double precio,
        Integer cantidad,
        String detallesAdicionales
) {
}
