package MS_PAQUETES.paquetes.services;

import MS_PAQUETES.paquetes.repository.DetallePRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class SeccionesService {

    private final DetallePRepository detallePaqueteRepository;
    public SeccionesService(DetallePRepository detallePaqueteRepository) {
        this.detallePaqueteRepository = detallePaqueteRepository;
    }

    @Transactional
    public void eliminarDetallePaquete(Integer id) {
        int deleted = detallePaqueteRepository.deleteByIdCustom(id);
        System.out.println("✅ Detalles eliminados: " + deleted);
    }



}
