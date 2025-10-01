package MS_Paquetes.paquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicios_seccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioSeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_seccion")
    private Integer idServicioSeccion;

    // Relación con DetallePaquetes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalles_paquetes", nullable = false)
    @JsonIgnore
    private DetallePaquetes detallePaquete;

    @Column(name = "id_servicio", nullable = false)
    private Integer idServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", insertable = false, updatable = false)
    private Servicio servicio;


    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "detalles_adicionales")
    private String detallesAdicionales;

    @Column(name = "precio")
    private Double precio;
}
