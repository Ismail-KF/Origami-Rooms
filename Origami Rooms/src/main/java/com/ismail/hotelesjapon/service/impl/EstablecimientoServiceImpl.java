package com.ismail.hotelesjapon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.repository.EstablecimientoRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.repository.ReservaRepository;
import com.ismail.hotelesjapon.service.EstablecimientoService;

import jakarta.transaction.Transactional;

/**
 * Servicio que implementa los métodos creados en {@link EstablecimientoService}
 * 
 * @author Ismail
 * @version 1.0
 * @see ClienteServiceImpl
 * @see HabitacionServiceImpl
 * @see HotelServiceImpl
 * @see ReservaServiceImpl
 * @see RyokanServiceImpl
 */
@Service
public class EstablecimientoServiceImpl implements EstablecimientoService {
    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Método que muestra todos los establecimientos registrados.
     * 
     * @return Lista de todos los establecimientos registrados
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Establecimiento> mostrarTodos() {
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();

        if (establecimientos.isEmpty()) {
            throw new RuntimeException("No se han encontrado establecimientos.");
        }

        return establecimientos;
    }

    /**
     * Método para buscar un establecimiento concreto por ID.
     * 
     * @param id Identificador único del establecimiento
     * @return Establecimiento si se ha encontrado
     * @throws RunTimeException si no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Establecimiento buscarPorId(Long id) {
        return establecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado."));
    }

    /**
     * Método que sirve para buscar establecimientos a través de diferentes filtros
     * como el nombre, la dirección, etc.
     * 
     * @param nombre                Nombre del establecimiento
     * @param direccion             Dirección en el establecimiento
     * @param region                Región en la que está el establecimiento
     * @param puntuacionMediaMinima Puntuación media del establecimiento
     * @return Una lista de establecimientos que concuerden con los filtros
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Establecimiento> buscarEstablecimientos(String nombre, String direccion, Region region,
            Double puntuacionMediaMinima) {
        return establecimientoRepository.buscarEstablecimientosPorFiltros(nombre, direccion, region,
                puntuacionMediaMinima);
    }

    /**
     * Método que añade un establecimiento nuevo. Si algún dato no está presente
     * lanza un error.
     * 
     * @param establecimientoACrear Establecimiento que se quiere añadir
     * @return Establecimiento recién añadido
     * @throws RunTimeException Si falta algún dato
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Establecimiento crearEstablecimiento(Establecimiento establecimientoACrear) {
        if (establecimientoACrear.getNombre() == null || establecimientoACrear.getNombre().isEmpty()) {
            throw new RuntimeException("El establecimiento debe tener un nombre.");
        }

        if (establecimientoACrear.getDescripcion() == null || establecimientoACrear.getDescripcion().isEmpty()) {
            throw new RuntimeException("El establecimiento debe tener una descripción.");
        }

        if (establecimientoACrear.getDireccion() == null || establecimientoACrear.getDireccion().isEmpty()) {
            throw new RuntimeException("El establecimiento debe tener una dirección asociada.");
        }

        if (establecimientoACrear.getRegion() == null) {
            throw new RuntimeException("El establecimiento debe tener una región asociada.");
        }

        establecimientoACrear.setHabitaciones(new ArrayList<>());
        establecimientoACrear.setPuntuaciones(new ArrayList<>());
        establecimientoACrear.calcularMedia();

        return establecimientoRepository.save(establecimientoACrear);
    }

    /**
     * Método para actualizar un establecimiento al completo
     * 
     * @param id                        ID único del establecimiento que se quiere
     *                                  modificar
     * @param establecimientoModificado Establecimiento con los datos nuevos que
     *                                  sustituirán a los antiguos
     * @return Establecimiento modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Establecimiento actualizarEstablecimiento(Long id, Establecimiento establecimientoModificado) {
        Establecimiento establecimientoAModificar = buscarPorId(id);

        establecimientoAModificar.setNombre(establecimientoModificado.getNombre());
        establecimientoAModificar.setDescripcion(establecimientoModificado.getDescripcion());
        establecimientoAModificar.setDireccion(establecimientoModificado.getDireccion());
        establecimientoAModificar.setRegion(establecimientoModificado.getRegion());

        if (establecimientoModificado.getPuntuaciones() != null) {
            establecimientoAModificar.setPuntuaciones(establecimientoModificado.getPuntuaciones());
            establecimientoAModificar.calcularMedia();
        }

        return establecimientoRepository.save(establecimientoAModificar);
    }

    /**
     * Método que actualiza parcialmente un establecimiento. Revisa si un dato no es
     * nulo y, en ese caso, lo actualiza.
     * 
     * @param id                        ID único del establecimiento que se quiere
     *                                  modificar
     * @param establecimientoModificado Establecimiento con los datos nuevos que
     *                                  sustituirán a los antiguos
     * @return Establecimiento modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Establecimiento actualizarEstablecimientoParcialmente(Long id, Establecimiento establecimientoModificado) {
        Establecimiento establecimientoAModificar = buscarPorId(id);

        if (establecimientoModificado.getNombre() != null) {
            establecimientoAModificar.setNombre(establecimientoModificado.getNombre());
        }

        if (establecimientoModificado.getDescripcion() != null) {
            establecimientoAModificar.setDescripcion(establecimientoModificado.getDescripcion());
        }

        if (establecimientoModificado.getDireccion() != null) {
            establecimientoAModificar.setDireccion(establecimientoModificado.getDireccion());
        }

        if (establecimientoModificado.getRegion() != null) {
            establecimientoAModificar.setRegion(establecimientoModificado.getRegion());
        }

        if (establecimientoModificado.getPuntuaciones() != null) {
            establecimientoAModificar.setPuntuaciones(establecimientoModificado.getPuntuaciones());
            establecimientoAModificar.calcularMedia();
        }

        return establecimientoRepository.save(establecimientoAModificar);
    }

    /**
     * Elimina un establecimiento a través de su ID si es que este existe, si no
     * lanza excepción.
     * 
     * @param id ID único del establecimiento que se quiere eliminar
     * @throws RunTimeException Si no se encuentra el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public void eliminarEstablecimiento(Long id) {
        Establecimiento establecimiento = buscarPorId(id);

        for (Habitacion habitacion : establecimiento.getHabitaciones()) {
            for (Reserva reserva : habitacion.getReservas()) {
                reserva.getHabitaciones().remove(habitacion);
                reservaRepository.save(reserva);
            }
            habitacion.getReservas().clear();
            habitacionRepository.save(habitacion);
        }

        establecimientoRepository.deleteById(id);
    }
}