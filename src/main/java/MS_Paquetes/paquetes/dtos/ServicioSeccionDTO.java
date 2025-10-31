package MS_PAQUETES.paquetes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioSeccionDTO {
    private Integer id_servicio_seccion;
    private Integer idServicio;
    private String detallesAdicionales;
    private Integer cantidad;
    private Double precio;
}
