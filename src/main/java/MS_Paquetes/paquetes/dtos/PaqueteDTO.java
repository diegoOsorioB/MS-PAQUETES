package MS_PAQUETES.paquetes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteDTO {
    private Integer idPaquete;
    private String nombrePaquete;
    private String descripcion;
    private String duracion;
    private Double precioPaquete;
    private List<DetalleDTO> detalles;
}
