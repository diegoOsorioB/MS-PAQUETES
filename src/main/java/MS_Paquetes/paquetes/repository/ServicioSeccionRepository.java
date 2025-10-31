package MS_PAQUETES.paquetes.repository;

import MS_PAQUETES.paquetes.entity.DetallePaquete;
import MS_PAQUETES.paquetes.entity.ServicioSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioSeccionRepository extends JpaRepository<ServicioSeccion, Integer> {
    List<ServicioSeccion> findByDetallePaquete(DetallePaquete detallePaquete);


    @Query("SELECT ss FROM ServicioSeccion ss " +
            "LEFT JOIN FETCH ss.servicio " +
            "WHERE ss.detallePaquete IN :secciones")
    List<ServicioSeccion> findServiciosBySecciones(@Param("secciones") List<DetallePaquete> secciones);

}
