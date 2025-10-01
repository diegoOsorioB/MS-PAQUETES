package MS_Paquetes.paquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalles_paquetes")
public class DetallePaquetes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detallesPaquetes")
    private Integer idDetallesPaquetes;

    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_paquete")
    @JsonIgnore
    private Paquete paquete;

    @OneToMany(mappedBy = "detallePaquete", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ServicioSeccion> servicios = new ArrayList<>();

}
