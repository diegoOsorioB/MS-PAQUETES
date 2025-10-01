package MS_Paquetes.paquetes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paquete")
    private Integer idPaquete;

    @Column(name = "nombre_paquete")
    private String nombrePaquete;

    private String descripcion;
    private String duracion;

    @Column(name = "precio_paquete")
    private Double precioPaquete;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetallePaquetes> secciones = new ArrayList<>();

    public List<DetallePaquetes> getSecciones() {
        return secciones;
    }



}
