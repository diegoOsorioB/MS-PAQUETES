package MS_Paquetes.paquetes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioSeccionDTO {
    private Integer idServicio;     // viene de otro microservicio
    private Integer cantidad;
    private String detallesAdicionales;
    private Double precio;

}