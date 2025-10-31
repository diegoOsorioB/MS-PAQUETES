package MS_PAQUETES.paquetes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleDTO {
    private Integer id_detalles_paquetes;
    private String titulo;
    private String descripcion;
    private List<ServicioSeccionDTO> servicios;
}
