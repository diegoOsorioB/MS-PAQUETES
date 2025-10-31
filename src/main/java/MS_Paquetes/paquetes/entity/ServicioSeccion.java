package MS_PAQUETES.paquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "servicios_seccion")
public class ServicioSeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_seccion")
    private Integer idServicioSeccion;

    @ManyToOne
    @JoinColumn(name = "id_detalles_paquetes", nullable = false)
    @JsonIgnore
    private DetallePaquete detallePaquete;

    @Column(name = "id_servicio", nullable = false)
    private Integer idServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio",insertable = false, updatable = false)
    private Servicio servicio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "detalles_adicionales")
    private String detallesAdicionales;

    @Column(name = "precio")
    private Double precio;
}
