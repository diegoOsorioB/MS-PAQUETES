package MS_PAQUETES.paquetes.controller;

import MS_PAQUETES.paquetes.services.SeccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalle")
public class DetalleSeccionController {
    private final SeccionesService seccionesService;

    @Autowired
    public DetalleSeccionController(SeccionesService seccionesService) {
        this.seccionesService = seccionesService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarSeccion(@PathVariable("id") Integer id)
    {
        System.out.println("Entro eliminando la seccion X2");
        seccionesService.eliminarDetallePaquete(id);
        System.out.println("Seccion eliminada");
    }
}
