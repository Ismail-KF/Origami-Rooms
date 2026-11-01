package com.ismail.hotelesjapon.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;
import com.ismail.hotelesjapon.repository.ClienteRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.repository.ReservaRepository;
import com.ismail.hotelesjapon.service.HabitacionService;
import com.ismail.hotelesjapon.service.ReservaService;
import jakarta.transaction.Transactional;

/**
 * Servicio que implementa los métodos creados en {@link ReservaService}
 *
 * @author Ismail
 * @version 1.0
 * @see ClienteServiceImpl
 * @see EstablecimientoServiceImpl
 * @see HabitacionServiceImpl
 * @see HotelServiceImpl
 * @see RyokanServiceImpl
 */
@Service
public class ReservaServiceImpl implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionService habitacionService;

    /**
     * Método que muestra todas las reservas registradas.
     *
     * @return Lista de todas las reservas registradas
     * @throws RuntimeException si no hay reservas registradas
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Reserva> mostrarTodas() {
        List<Reserva> reservas = reservaRepository.findAll();

        if (reservas.isEmpty()) {
            throw new RuntimeException("No hay reservas registradas.");
        }

        return reservas;
    }

    /**
     * Método para buscar una reserva concreta por ID.
     *
     * @param id Identificador único de la reserva
     * @return Reserva si se ha encontrado
     * @throws RuntimeException si no se ha encontrado
     * @author Ismail
     * @since 1.0
     */

    @Override
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada."));
    }

    /**
     * Método que sirve para buscar reservas a través de diferentes filtros
     * como el cliente, el estado o las fechas.
     *
     * @param clienteId   ID del cliente que realizó la reserva
     * @param estado      Estado de la reserva
     * @param fechaInicio Fecha de entrada de la reserva
     * @param fechaFin    Fecha de salida de la reserva
     * @return Lista de reservas que concuerden con los filtros
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Reserva> buscarReservas(Long clienteId, Estado estado, LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.buscarReservasPorFiltros(clienteId, estado, fechaInicio, fechaFin);
    }

    /**
     * Método que calcula el precio total de una reserva sumando el precio
     * por noche de todas las habitaciones incluidas y multiplicando por
     * el número de días de estancia.
     *
     * @param reserva Reserva de la que se quiere calcular el precio total
     * @return Precio total de la reserva
     * @author Ismail
     * @since 1.0
     */
    @Override
    public double calcularPrecioTotal(Reserva reserva) {
        if (reserva.getHabitaciones() == null || reserva.getHabitaciones().isEmpty()) {
            return 0.0;
        }
        Double suma = 0.0;
        long dias = ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());

        for (Habitacion habitacion : reserva.getHabitaciones()) {
            suma += habitacion.getPrecioPorNoche();
        }

        return suma * dias;
    }

    /**
     * Método privado que libera las habitaciones asociadas a una reserva
     * eliminando la reserva de la lista de reservas de cada habitación.
     *
     * @param reserva Reserva cuyas habitaciones se quieren liberar
     * @author Ismail
     * @since 1.0
     */
    private void liberarHabitacion(Reserva reserva) {
        for (Habitacion habitacion : reserva.getHabitaciones()) {
            habitacion.getReservas().remove(reserva);
            habitacionRepository.save(habitacion);
        }
    }

    /**
     * Método que crea una nueva reserva asociándola a un cliente y a una
     * o varias habitaciones. Comprueba que las habitaciones existen y que
     * están disponibles para las fechas indicadas antes de crearla.
     *
     * @param reservaACrear  Reserva que se quiere crear
     * @param clienteId      ID del cliente que realiza la reserva
     * @param habitacionesId Lista de IDs de las habitaciones a reservar
     * @return Reserva recién creada con estado PENDIENTE y precio calculado
     * @throws RuntimeException si el cliente no existe, alguna habitación
     *                          no existe o no está disponible para esas fechas
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public Reserva crearReserva(Reserva reservaACrear, Long clienteId, List<Long> habitacionesId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        List<Habitacion> habitaciones = new ArrayList<>();

        for (Long habitacionId : habitacionesId) {
            Habitacion habitacion = habitacionRepository.findById(habitacionId)
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada: " + habitacionId));

            if (!habitacionService.estaDisponible(habitacionId, reservaACrear.getFechaEntrada(),
                    reservaACrear.getFechaSalida())) {
                throw new RuntimeException("La habitación no está disponible para esas fechas.");
            }

            habitaciones.add(habitacion);
        }

        if (reservaACrear.getEstadoReserva() == null) {
            reservaACrear.setEstadoReserva(Reserva.Estado.PENDIENTE);
        } else {
            reservaACrear.setEstadoReserva(reservaACrear.getEstadoReserva());
        }

        reservaACrear.setCliente(cliente);
        reservaACrear.setHabitaciones(habitaciones);
        reservaACrear.setPrecioTotal(calcularPrecioTotal(reservaACrear));

        return reservaRepository.save(reservaACrear);
    }

    /**
     * Método para actualizar una reserva al completo. Si el nuevo estado
     * es CANCELADA libera automáticamente las habitaciones asociadas.
     *
     * @param id                ID único de la reserva que se quiere modificar
     * @param reservaModificada Reserva con los datos nuevos que sustituirán
     *                          a los antiguos
     * @return Reserva modificada
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Reserva actualizarReserva(Long id, Reserva reservaModificada) {
        Reserva reservaAModificar = buscarPorId(id);

        reservaAModificar.setFechaEntrada(reservaModificada.getFechaEntrada());
        reservaAModificar.setFechaSalida(reservaModificada.getFechaSalida());
        reservaAModificar.setEstadoReserva(reservaModificada.getEstadoReserva());
        reservaAModificar.setPrecioTotal(calcularPrecioTotal(reservaModificada));
        reservaAModificar.setNumeroHabitaciones(reservaModificada.getNumeroHabitaciones());
        reservaAModificar.setCliente(reservaModificada.getCliente());
        reservaAModificar.setHabitaciones(reservaModificada.getHabitaciones());

        if (reservaModificada.getEstadoReserva() == Reserva.Estado.CANCELADA) {
            liberarHabitacion(reservaAModificar);
        }

        return reservaRepository.save(reservaAModificar);
    }

    /**
     * Método que actualiza parcialmente una reserva. Revisa si un dato no es
     * nulo y, en ese caso, lo actualiza. Si el nuevo estado es CANCELADA
     * libera automáticamente las habitaciones asociadas.
     *
     * @param id                ID único de la reserva que se quiere modificar
     * @param reservaModificada Reserva con los datos nuevos que sustituirán
     *                          a los antiguos
     * @return Reserva modificada
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Reserva actualizarReservaParcialmente(Long id, Reserva reservaModificada) {
        Reserva reservaAModificar = buscarPorId(id);

        if (reservaModificada.getFechaEntrada() != null) {
            reservaAModificar.setFechaEntrada(reservaModificada.getFechaEntrada());
            reservaAModificar.setPrecioTotal(calcularPrecioTotal(reservaAModificar));
        }

        if (reservaModificada.getFechaSalida() != null) {
            reservaAModificar.setFechaSalida(reservaModificada.getFechaSalida());
            reservaAModificar.setPrecioTotal(calcularPrecioTotal(reservaAModificar));
        }

        if (reservaModificada.getEstadoReserva() != null) {
            reservaAModificar.setEstadoReserva(reservaModificada.getEstadoReserva());

            if (reservaModificada.getEstadoReserva() == Reserva.Estado.CANCELADA) {
                liberarHabitacion(reservaAModificar);
            }
        }

        if (reservaModificada.getNumeroHabitaciones() > 0) {
            reservaAModificar.setNumeroHabitaciones(reservaModificada.getNumeroHabitaciones());
        }

        if (reservaModificada.getCliente() != null) {
            reservaAModificar.setCliente(reservaModificada.getCliente());
        }

        if (reservaModificada.getHabitaciones() != null) {
            reservaAModificar.setHabitaciones(reservaModificada.getHabitaciones());
            reservaAModificar.setPrecioTotal(calcularPrecioTotal(reservaModificada));
        }

        return reservaRepository.save(reservaAModificar);
    }

    /**
     * Elimina una reserva a través de su ID si es que esta existe, si no
     * lanza excepción.
     *
     * @param id ID único de la reserva que se quiere eliminar
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @Override
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("No se ha encontrado ninguna reserva con el id: " + id);
        }

        reservaRepository.deleteById(id);
    }
}