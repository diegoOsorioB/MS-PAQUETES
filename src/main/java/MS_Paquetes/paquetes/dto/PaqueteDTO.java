package MS_Paquetes.paquetes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteDTO {
    private Integer idPaquete; // null cuando es creación
    private String nombrePaquete;
    private String descripcion;
    private String duracion;
    private Double precioPaquete;
    private List<DetalleDTO> detalles;


}