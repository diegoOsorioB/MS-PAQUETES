package MS_PAQUETES.paquetes.repository;

import MS_PAQUETES.paquetes.entity.DetallePaquete;
import MS_PAQUETES.paquetes.entity.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePaqueteRepository extends JpaRepository<DetallePaquete, Integer> {
    List<DetallePaquete> findByPaquete(Paquete paquete);

}
