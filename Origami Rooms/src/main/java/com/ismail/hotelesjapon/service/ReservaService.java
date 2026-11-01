package com.ismail.hotelesjapon.service;

import java.time.LocalDate;
import java.util.List;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;

public interface ReservaService {
    List<Reserva> mostrarTodas();

    List<Reserva> buscarReservas(Long clienteId, Estado estado, LocalDate fechaInicio, LocalDate fechaFin);

    Reserva buscarPorId(Long id);

    Reserva crearReserva(Reserva reserva, Long clienteId, List<Long> habitacionesId);

    Reserva actualizarReserva(Long id, Reserva reserva);

    Reserva actualizarReservaParcialmente(Long id, Reserva reserva);

    double calcularPrecioTotal(Reserva reserva);

    void eliminarReserva(Long id);
}