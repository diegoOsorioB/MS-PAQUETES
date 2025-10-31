package MS_PAQUETES.paquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "detalles_paquetes")
public class DetallePaquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalles_paquetes")
    private Integer idDetallePaquete;

    private String  titulo;
    private String  descripcion;

    @ManyToOne
    @JoinColumn(name = "id_paquete")
    @JsonIgnore
    private Paquete paquete;

    @OneToMany(mappedBy = "detallePaquete",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<ServicioSeccion> servicios=new ArrayList<>();
}
