package MS_PAQUETES.paquetes.repository;

import MS_PAQUETES.paquetes.entity.ServicioSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioSeccion, Integer> {
}
