package MS_Paquetes.paquetes.controller;

import MS_Paquetes.paquetes.dto.PaqueteResponse;
import MS_Paquetes.paquetes.entity.Paquete;
import MS_Paquetes.paquetes.dto.PaqueteDTO;
import MS_Paquetes.paquetes.service.PaqueteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    // ======== CONSULTAR ========

    @GetMapping
    public List<PaqueteResponse> obtenerPaquetesConSecciones() {
        return paqueteService.listarPaquetesConSecciones();
    }


    @GetMapping("/{id}")
    public Paquete obtenerPaquetePorId(@PathVariable("id") int idPaquete) {
        return paqueteService.buscarPaquetePorId(idPaquete);
    }

    // ======== CREAR ========

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paquete crearPaquete(@RequestBody PaqueteDTO paqueteDTO) {
        return paqueteService.crearPaquete(paqueteDTO);
    }


    @PutMapping("/{id}")
    public Paquete actualizarPaquete(@PathVariable("id") int idPaquete,
                                     @RequestBody PaqueteDTO paqueteDTO) {
        return paqueteService.actualizarPaquete(idPaquete, paqueteDTO);
    }

    // ======== ELIMINAR ========

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPaquete(@PathVariable("id") int idPaquete) {
        paqueteService.eliminarPaquete(idPaquete);
    }
}
