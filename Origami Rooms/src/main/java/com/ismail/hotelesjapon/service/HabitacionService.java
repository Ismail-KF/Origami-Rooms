package com.ismail.hotelesjapon.service;

import java.time.LocalDate;
import java.util.List;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;

public interface HabitacionService {
    List<Habitacion> mostrarTodas();

    List<Habitacion> buscarHabitaciones(Tipo tipo, Integer capacidadMinima, Double precioMaximo);

    Habitacion buscarPorId(Long id);

    Habitacion crearHabitacion(Habitacion habitacion, Long establecimientoId);

    Habitacion actualizarHabitacion(Long id, Habitacion habitacion);

    Habitacion actualizarHabitacionParcialmente(Long id, Habitacion habitacion);

    boolean estaDisponible(Long habitacionId, LocalDate fechaEntrada, LocalDate fechaSalida);

    void eliminarHabitacion(Long id);
}