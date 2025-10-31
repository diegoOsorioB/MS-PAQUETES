package MS_PAQUETES.paquetes.controller;

import MS_PAQUETES.paquetes.dtos.PaqueteDTO;
import MS_PAQUETES.paquetes.dtos.PaqueteResponse;
import MS_PAQUETES.paquetes.dtos.SeccionResponse;
import MS_PAQUETES.paquetes.entity.Paquete;
import MS_PAQUETES.paquetes.services.PaqueteService;
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

    @GetMapping
    public List<PaqueteResponse> getSecciones(){
        return paqueteService.listarPaquetesConSecciones();
    }

    @GetMapping("/{id}")
    public PaqueteResponse obtenerPaquetePorID(@PathVariable("id") Integer idPaquete)
    {
        return paqueteService.buscarPaquetePorId(idPaquete);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paquete crearPaquete(@RequestBody PaqueteDTO paquete)
    {
        return paqueteService.crearPaquete(paquete);
    }

    @PutMapping("/{id}")
    public Paquete actualizar(@PathVariable("id")int idPaquete,
                              @RequestBody PaqueteDTO paquete){
        return paqueteService.actualizarPaquete(idPaquete,paquete);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id")int idPaquete){
        paqueteService.eliminarPaquete(idPaquete);
    }

}
