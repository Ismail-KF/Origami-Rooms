package com.ismail.hotelesjapon.service;

import java.util.List;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Hotel;

public interface HotelService {
    List<Hotel> mostrarTodos();

    List<Hotel> buscarHoteles(Region region, Double puntuacionMediaMinima, Integer estrellas, Boolean tieneRestaurante,
            Boolean tieneGimnasio, Boolean tieneServicioHabitaciones);

    Hotel buscarPorId(Long id);

    Hotel crearHotel(Hotel hotel);

    Hotel actualizarHotel(Long id, Hotel hotel);

    Hotel actualizarHotelParcialmente(Long id, Hotel hotel);

    void eliminarHotel(Long id);
}