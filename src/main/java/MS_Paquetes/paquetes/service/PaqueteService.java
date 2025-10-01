package MS_Paquetes.paquetes.service;

import MS_Paquetes.paquetes.entity.*;
import MS_Paquetes.paquetes.repository.*;
import MS_Paquetes.paquetes.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaqueteService {

    private final PaqueteRepository paqueteRepository;
    private final DetallePaqueteRepository detalleRepository;
    private final ServicioSeccionRepository serviciosSeccionRepository;

    public PaqueteService(PaqueteRepository paqueteRepository,
                          DetallePaqueteRepository detalleRepository,
                          ServicioSeccionRepository serviciosSeccionRepository) {
        this.paqueteRepository = paqueteRepository;
        this.detalleRepository = detalleRepository;
        this.serviciosSeccionRepository = serviciosSeccionRepository;
    }

    public List<Paquete> listarPaquetes() {
        return paqueteRepository.findAll();
    }

    public Paquete buscarPaquetePorId(int idPaquete) {
        return paqueteRepository.findById(idPaquete)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Paquete no encontrado"));
    }


    @Transactional
    public Paquete crearPaquete(PaqueteDTO dto) {
        if (dto.getIdPaquete() != null &&
                paqueteRepository.existsById(dto.getIdPaquete())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ya existe un paquete con ese ID");
        }

        Paquete paquete = new Paquete();
        paquete.setNombrePaquete(dto.getNombrePaquete());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setDuracion(dto.getDuracion());
        paquete.setPrecioPaquete(dto.getPrecioPaquete());

        paquete = paqueteRepository.save(paquete);

        if (dto.getDetalles() != null) {
            for (DetalleDTO detDto : dto.getDetalles()) {
                DetallePaquetes detalle = new DetallePaquetes();
                detalle.setPaquete(paquete);
                detalle.setTitulo(detDto.getTitulo());
                detalle.setDescripcion(detDto.getDescripcion());
                detalle = detalleRepository.save(detalle);

                if (detDto.getServicios() != null) {
                    for (ServicioSeccionDTO sDto : detDto.getServicios()) {
                        ServicioSeccion ss = new ServicioSeccion();
                        ss.setDetallePaquete(detalle);
                        ss.setIdServicio(sDto.getIdServicio()); // FK a MS de servicios
                        ss.setCantidad(sDto.getCantidad());
                        ss.setDetallesAdicionales(sDto.getDetallesAdicionales());
                        ss.setPrecio(sDto.getPrecio());
                        serviciosSeccionRepository.save(ss);
                    }
                }
            }
        }

        return paquete;
    }

    @Transactional
    public Paquete actualizarPaquete(Integer id, PaqueteDTO dto) {
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Paquete no encontrado"));


        paquete.setNombrePaquete(dto.getNombrePaquete());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setDuracion(dto.getDuracion());
        paquete.setPrecioPaquete(dto.getPrecioPaquete());

        //detalleRepository.deleteAll(paquete.());

        if (dto.getDetalles() != null) {
            for (DetalleDTO detDto : dto.getDetalles()) {
                DetallePaquetes detalle = new DetallePaquetes();
                detalle.setPaquete(paquete);
                detalle.setTitulo(detDto.getTitulo());
                detalle.setDescripcion(detDto.getDescripcion());
                detalle = detalleRepository.save(detalle);

                if (detDto.getServicios() != null) {
                    for (ServicioSeccionDTO sDto : detDto.getServicios()) {
                        ServicioSeccion ss = new ServicioSeccion();
                        ss.setDetallePaquete(detalle);
                        ss.setIdServicio(sDto.getIdServicio());
                        ss.setCantidad(sDto.getCantidad());
                        ss.setDetallesAdicionales(sDto.getDetallesAdicionales());
                        ss.setPrecio(sDto.getPrecio());
                        serviciosSeccionRepository.save(ss);
                    }
                }
            }
        }

        return paqueteRepository.save(paquete);
    }

    @Transactional
    public void eliminarPaquete(Integer id) {
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Paquete no encontrado"));
        paqueteRepository.delete(paquete);
    }

    public List<PaqueteResponse> listarPaquetesConSecciones() {
        return paqueteRepository.findAll().stream()
                .map(paquete -> {
                    List<SeccionResponse> secciones = paquete.getSecciones().stream()
                            .map(seccion -> {
                                List<ServicioResponse> servicios = seccion.getServicios().stream()
                                        .map(ss -> new ServicioResponse(
                                                ss.getIdServicio().intValue(),
                                                ss.getServicio() != null ? ss.getServicio().getNombreServicio() : null,
                                                ss.getServicio() != null ? ss.getServicio().getDescripcion() : null,
                                                ss.getServicio() != null ? ss.getServicio().getPrecio() : null,
                                                ss.getCantidad(),
                                                ss.getDetallesAdicionales()
                                        ))
                                        .toList();

                                return new SeccionResponse(
                                        seccion.getIdDetallesPaquetes(),
                                        seccion.getTitulo(),
                                        seccion.getDescripcion(),
                                        servicios
                                );
                            }).toList();

                    return new PaqueteResponse(
                            paquete.getIdPaquete(),
                            paquete.getNombrePaquete(),
                            paquete.getDescripcion(),
                            paquete.getDuracion(),
                            paquete.getPrecioPaquete(),
                            secciones
                    );
                }).toList();
    }



}
