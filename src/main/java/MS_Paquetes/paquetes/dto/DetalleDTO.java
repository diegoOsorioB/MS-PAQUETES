package MS_Paquetes.paquetes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDTO {
    private String titulo;
    private String descripcion;
    private List<ServicioSeccionDTO> servicios;

}
