package MS_PAQUETES.paquetes.services;


import MS_PAQUETES.paquetes.entity.*;
import MS_PAQUETES.paquetes.exception.PaqueteException;
import MS_PAQUETES.paquetes.repository.*;
import MS_PAQUETES.paquetes.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Paquete buscarPaqueteEntityPorId(Integer idPaquete) {
        Paquete paquete = paqueteRepository.findByIdWithSecciones(idPaquete)
                .orElseThrow(() -> new PaqueteException.PaqueteNotFound(idPaquete));

        if (paquete.getSecciones() != null && !paquete.getSecciones().isEmpty()) {
            cargarServiciosParaSecciones(paquete.getSecciones());
        }

        return paquete;
    }

    public PaqueteResponse buscarPaquetePorId(Integer idPaquete) {
        Paquete paquete = buscarPaqueteEntityPorId(idPaquete);
        return convertirPaqueteAResponse(paquete);
    }

    private void cargarServiciosParaSecciones(List<DetallePaquete> secciones) {
        List<ServicioSeccion> servicios = serviciosSeccionRepository
                .findServiciosBySecciones(secciones);

        Map<Integer, List<ServicioSeccion>> serviciosPorSeccion = servicios.stream()
                .collect(Collectors.groupingBy(
                        ss -> ss.getDetallePaquete().getIdDetallePaquete()
                ));

        for (DetallePaquete seccion : secciones) {
            List<ServicioSeccion> serviciosDeSeccion = serviciosPorSeccion
                    .getOrDefault(seccion.getIdDetallePaquete(), new ArrayList<>());
            seccion.setServicios(serviciosDeSeccion);
        }
    }

    private PaqueteResponse convertirPaqueteAResponse(Paquete paquete) {
        List<SeccionResponse> secciones = paquete.getSecciones().stream()
                .map(this::convertirSeccionAResponse)
                .toList();

        return new PaqueteResponse(
                paquete.getIdPaquete(),
                paquete.getNombrePaquete(),
                paquete.getDescripcion(),
                paquete.getDuracion(),
                paquete.getPrecioPaquete(),
                secciones
        );
    }

    private SeccionResponse convertirSeccionAResponse(DetallePaquete seccion) {
        List<ServicioResponse> servicios = seccion.getServicios().stream()
                .map(this::convertirServicioAResponse)
                .toList();

        return new SeccionResponse(
                seccion.getIdDetallePaquete(),
                seccion.getTitulo(),
                seccion.getDescripcion(),
                servicios
        );
    }

    private ServicioResponse convertirServicioAResponse(ServicioSeccion servicioSeccion) {
        String nombreServicio = null;
        String descripcionServicio = null;
        Double precioServicio = null;

        if (servicioSeccion.getServicio() != null) {
            nombreServicio = servicioSeccion.getServicio().getNombreServicio();
            descripcionServicio = servicioSeccion.getServicio().getDescripcion();
            precioServicio = servicioSeccion.getServicio().getPrecioServicio();
        }

        return new ServicioResponse(
                servicioSeccion.getIdServicio().intValue(),
                nombreServicio,
                descripcionServicio,
                precioServicio,
                servicioSeccion.getCantidad(),
                servicioSeccion.getDetallesAdicionales()
        );
    }


    @Transactional
    public Paquete crearPaquete(PaqueteDTO dto) {
        if (dto.getIdPaquete() != null &&
                paqueteRepository.existsById(dto.getIdPaquete())) {
            throw new PaqueteException.InvalidDataException("Paquete ya existe");
        }

        Paquete paquete = new Paquete();
        paquete.setNombrePaquete(dto.getNombrePaquete());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setDuracion(dto.getDuracion());
        paquete.setPrecioPaquete(dto.getPrecioPaquete());

        paquete = paqueteRepository.save(paquete);

        if (dto.getDetalles() != null) {
            for (DetalleDTO detDto : dto.getDetalles()) {
                DetallePaquete detalle = new DetallePaquete();
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

        return paquete;
    }

    @Transactional
    public Paquete actualizarPaquete(Integer id, PaqueteDTO dto) {

        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new PaqueteException.PaqueteNotFound(id));

        paquete.setNombrePaquete(dto.getNombrePaquete());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setDuracion(dto.getDuracion());
        paquete.setPrecioPaquete(dto.getPrecioPaquete());

        if (dto.getDetalles() != null) {

            for (int i = 0; i < dto.getDetalles().size(); i++) {
                DetalleDTO detDto = dto.getDetalles().get(i);
                System.out.println("Procesando detalle " + i + " - ID: " + detDto.getId_detalles_paquetes());

                DetallePaquete detalle;

                if (detDto.getId_detalles_paquetes() != null) {
                    System.out.println("Buscando detalle existente con ID: " + detDto.getId_detalles_paquetes());
                    detalle = detalleRepository.findById(detDto.getId_detalles_paquetes())
                            .orElseThrow(() -> new PaqueteException.DetalleNotFound(detDto.getId_detalles_paquetes()));
                    detalle.setTitulo(detDto.getTitulo());
                    detalle.setDescripcion(detDto.getDescripcion());
                    detalle = detalleRepository.save(detalle);
                    System.out.println("Detalle actualizado: " + detalle.getIdDetallePaquete());
                } else {
                    System.out.println("Creando nuevo detalle");
                    detalle = new DetallePaquete();
                    detalle.setPaquete(paquete);
                    detalle.setTitulo(detDto.getTitulo());
                    detalle.setDescripcion(detDto.getDescripcion());
                    detalle = detalleRepository.save(detalle);
                    System.out.println("Nuevo detalle creado con ID: " + detalle.getIdDetallePaquete());
                }

                if (detDto.getServicios() != null) {

                    List<Integer> idsEnviados = detDto.getServicios().stream()
                            .map(ServicioSeccionDTO::getId_servicio_seccion)
                            .filter(idServicio -> idServicio != null)
                            .toList();


                    List<ServicioSeccion> serviciosActuales = serviciosSeccionRepository.findByDetallePaquete(detalle);

                    for (ServicioSeccion actual : serviciosActuales) {
                        if (!idsEnviados.contains(actual.getIdServicioSeccion())) {
                            System.out.println("Eliminando servicio: " + actual.getIdServicioSeccion());
                            serviciosSeccionRepository.delete(actual);
                        }
                    }

                    for (int j = 0; j < detDto.getServicios().size(); j++) {
                        ServicioSeccionDTO sDto = detDto.getServicios().get(j);
                        ServicioSeccion ss;

                        if (sDto.getId_servicio_seccion() != null) {
                            try {
                                ss = serviciosSeccionRepository.findById(sDto.getId_servicio_seccion())
                                        .orElseThrow();

                                if (ss.getDetallePaquete() == null ||
                                        !ss.getDetallePaquete().getIdDetallePaquete().equals(detalle.getIdDetallePaquete())) {
                                    ss = new ServicioSeccion();
                                    ss.setDetallePaquete(detalle);
                                }
                            } catch (Exception e) {
                                ss = new ServicioSeccion();
                                ss.setDetallePaquete(detalle);
                            }
                        } else {
                            ss = new ServicioSeccion();
                            ss.setDetallePaquete(detalle);
                        }

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
                .orElseThrow(() -> new PaqueteException.PaqueteNotFound(id));
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
                                                ss.getServicio() != null ? ss.getServicio().getPrecioServicio() : null,
                                                ss.getCantidad(),
                                                ss.getDetallesAdicionales()
                                        ))
                                        .toList();

                                return new SeccionResponse(
                                        seccion.getIdDetallePaquete(),
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
