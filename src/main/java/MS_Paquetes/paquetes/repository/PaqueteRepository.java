package MS_PAQUETES.paquetes.repository;

import MS_PAQUETES.paquetes.entity.DetallePaquete;
import MS_PAQUETES.paquetes.entity.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {
    @Query("SELECT p FROM Paquete p " +
                  "LEFT JOIN FETCH p.secciones " +
                  "WHERE p.idPaquete = :id")
    Optional<Paquete> findByIdWithSecciones(@Param("id") Integer id);
}
