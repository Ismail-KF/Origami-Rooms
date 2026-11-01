package com.ismail.hotelesjapon.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.repository.EstablecimientoRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.service.HabitacionService;

import jakarta.transaction.Transactional;

/**
 * Servicio que implementa los métodos creados en {@link HabitacionService}
 * 
 * @author Ismail
 * @version 1.0
 * @see ClienteServiceImpl
 * @see EstablecimientoServiceImpl
 * @see HotelServiceImpl
 * @see ReservaServiceImpl
 * @see RyokanServiceImpl
 */
@Service
public class HabitacionServiceImpl implements HabitacionService {
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    /**
     * Método que muestra todas las habitaciones registradas.
     * 
     * @return Lista de todas las habitaciones registradas
     * @throws RuntimeException si no se han encontrado habitaciones
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Habitacion> mostrarTodas() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();

        if (habitaciones.isEmpty()) {
            throw new RuntimeException("No se han encontrado habitaciones.");
        }

        return habitaciones;
    }

    /**
     * Método para buscar una habitación concreta por ID.
     * 
     * @param id Identificador único de la habitación
     * @return Habitación si se ha encontrado
     * @throws RuntimeException si no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Habitacion buscarPorId(Long id) {
        return habitacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Habitación no encontrada."));
    }

    /**
     * Método que sirve para buscar habitaciones a través de diferentes filtros
     * como el tipo, la capacidad mínima o el precio máximo.
     * 
     * @param tipo            Tipo de habitación
     * @param capacidadMinima Capacidad mínima de la habitación
     * @param precioMaximo    Precio máximo por noche
     * @return Lista de habitaciones que concuerden con los filtros
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Habitacion> buscarHabitaciones(Tipo tipo, Integer capacidadMinima, Double precioMaximo) {
        return habitacionRepository.buscarHabitacionesPorFiltros(tipo, capacidadMinima, precioMaximo);
    }

    /**
     * Método que añade una habitación nueva a un establecimiento.
     * Si algún dato no está presente lanza un error.
     * 
     * @param habitacionACrear  Habitación que se quiere añadir
     * @param establecimientoId ID del establecimiento al que pertenece
     * @return Habitación recién añadida
     * @throws RuntimeException si falta algún dato o no se encuentra el
     *                          establecimiento
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Habitacion crearHabitacion(Habitacion habitacionACrear, Long establecimientoId) {

        if (habitacionACrear.getNumeroHabitacion() == null || habitacionACrear.getNumeroHabitacion().isEmpty()
                || habitacionACrear.getNumeroHabitacion().contains("[a-zA-Z]")) {
            throw new RuntimeException("La habitación debe tener un número.");
        }

        if (habitacionACrear.getTipoHabitacion() == null) {
            throw new RuntimeException("La habitación debe tener un tipo.");
        }

        if (habitacionACrear.getCapacidad() <= 0) {
            throw new RuntimeException("La habitación debe tener una capacidad asociada.");
        }

        if (habitacionACrear.getPrecioPorNoche() <= 0.0) {
            throw new RuntimeException("La habitación debe tener un precio por noche asociado.");
        }

        habitacionACrear.setReservas(new ArrayList<>());
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));
        habitacionACrear.setEstablecimiento(establecimiento);

        return habitacionRepository.save(habitacionACrear);
    }

    /**
     * Método para actualizar una habitación al completo.
     * 
     * @param id                   ID único de la habitación que se quiere modificar
     * @param habitacionModificada Habitación con los datos nuevos que
     *                             sustituirán a los antiguos
     * @return Habitación modificada
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Habitacion actualizarHabitacion(Long id, Habitacion habitacionModificada) {
        Habitacion habitacionAModificar = buscarPorId(id);

        habitacionAModificar.setNumeroHabitacion(habitacionModificada.getNumeroHabitacion());
        habitacionAModificar.setTipoHabitacion(habitacionModificada.getTipoHabitacion());
        habitacionAModificar.setCapacidad(habitacionModificada.getCapacidad());
        habitacionAModificar.setPrecioPorNoche(habitacionModificada.getPrecioPorNoche());
        habitacionAModificar.setReservas(habitacionModificada.getReservas());
        habitacionAModificar.setEstablecimiento(habitacionModificada.getEstablecimiento());

        return habitacionRepository.save(habitacionAModificar);
    }

    /**
     * Método que actualiza parcialmente una habitación. Revisa si un dato no es
     * nulo y, en ese caso, lo actualiza.
     * 
     * @param id                   ID único de la habitación que se quiere modificar
     * @param habitacionModificada Habitación con los datos nuevos que
     *                             sustituirán a los antiguos
     * @return Habitación modificada
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Habitacion actualizarHabitacionParcialmente(Long id, Habitacion habitacionModificada) {
        Habitacion habitacionAModificar = buscarPorId(id);

        if (habitacionModificada.getNumeroHabitacion() != null) {
            habitacionAModificar.setNumeroHabitacion(habitacionModificada.getNumeroHabitacion());
        }

        if (habitacionModificada.getTipoHabitacion() != null) {
            habitacionAModificar.setTipoHabitacion(habitacionModificada.getTipoHabitacion());
        }

        if (habitacionModificada.getCapacidad() > 0) {
            habitacionAModificar.setCapacidad(habitacionModificada.getCapacidad());
        }

        if (habitacionModificada.getPrecioPorNoche() > 0.0) {
            habitacionAModificar.setPrecioPorNoche(habitacionModificada.getPrecioPorNoche());
        }

        if (habitacionModificada.getReservas() != null) {
            habitacionAModificar.setReservas(habitacionModificada.getReservas());
        }

        if (habitacionModificada.getEstablecimiento() != null) {
            habitacionAModificar.setEstablecimiento(habitacionModificada.getEstablecimiento());
        }

        return habitacionRepository.save(habitacionAModificar);
    }

    /**
     * Método que comprueba si una habitación está disponible para unas fechas
     * concretas revisando que no haya reservas activas que se solapen.
     * 
     * @param habitacionId ID único de la habitación
     * @param fechaEntrada Fecha de entrada deseada
     * @param fechaSalida  Fecha de salida deseada
     * @return true si la habitación está disponible, false si no lo está
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public boolean estaDisponible(Long habitacionId, LocalDate fechaEntrada, LocalDate fechaSalida) {
        boolean estaDisponible = true;
        Habitacion habitacionABuscar = buscarPorId(habitacionId);

        for (Reserva reserva : habitacionABuscar.getReservas()) {
            if (reserva.getEstadoReserva() != Reserva.Estado.CANCELADA) {
                boolean seSolapan = fechaEntrada.isBefore(reserva.getFechaSalida())
                        && fechaSalida.isAfter(reserva.getFechaEntrada());
                if (seSolapan)
                    estaDisponible = false;
            }
        }

        return estaDisponible;
    }

    /**
     * Elimina una habitación a través de su ID si es que esta existe, si no
     * lanza excepción.
     * 
     * @param id ID único de la habitación que se quiere eliminar
     * @throws RuntimeException si no se encuentra la habitación
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public void eliminarHabitacion(Long id) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ninguna habitación con el id: " + id));

        Establecimiento establecimiento = habitacion.getEstablecimiento();

        if (establecimiento != null) {
            establecimiento.getHabitaciones().remove(habitacion);
            establecimientoRepository.save(establecimiento);
        }
    }
}