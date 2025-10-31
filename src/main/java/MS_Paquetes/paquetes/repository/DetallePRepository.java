package MS_PAQUETES.paquetes.repository;

import MS_PAQUETES.paquetes.entity.DetallePaquete;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePRepository extends CrudRepository<DetallePaquete, Integer> {
    @Modifying
    @Query("DELETE FROM DetallePaquete d WHERE d.idDetallePaquete = :id")
    int deleteByIdCustom(Integer id);
}
