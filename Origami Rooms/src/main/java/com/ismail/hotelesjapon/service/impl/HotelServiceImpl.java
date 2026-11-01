package com.ismail.hotelesjapon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Hotel;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.repository.HotelRepository;
import com.ismail.hotelesjapon.repository.ReservaRepository;
import com.ismail.hotelesjapon.service.HotelService;

import jakarta.transaction.Transactional;

/**
 * Servicio que implementa los métodos creados en {@link HotelService}
 *
 * @author Ismail
 * @version 1.0
 * @see ClienteServiceImpl
 * @see EstablecimientoServiceImpl
 * @see HabitacionServiceImpl
 * @see ReservaServiceImpl
 * @see RyokanServiceImpl
 */
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Método que muestra todos los hoteles registrados.
     *
     * @return Lista de todos los hoteles registrados
     * @throws RuntimeException si no hay hoteles registrados
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Hotel> mostrarTodos() {
        List<Hotel> hoteles = hotelRepository.findAll();

        if (hoteles.isEmpty()) {
            throw new RuntimeException("No hay hoteles registrados.");
        }

        return hoteles;
    }

    /**
     * Método para buscar un hotel concreto por ID.
     *
     * @param id Identificador único del hotel
     * @return Hotel si se ha encontrado
     * @throws RuntimeException si no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Hotel buscarPorId(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel no encontrado."));
    }

    /**
     * Método que sirve para buscar hoteles a través de diferentes filtros
     * como la región, la puntuación media, las estrellas o los servicios
     * disponibles.
     *
     * @param region                    Región de Japón donde se encuentra el hotel
     * @param puntuacionMediaMinima     Puntuación media mínima del hotel
     * @param estrellas                 Número de estrellas del hotel
     * @param tieneRestaurante          Si el hotel tiene restaurante
     * @param tieneGimnasio             Si el hotel tiene gimnasio
     * @param tieneServicioHabitaciones Si el hotel tiene servicio de habitaciones
     * @return Lista de hoteles que concuerden con los filtros
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Hotel> buscarHoteles(Region region, Double puntuacionMediaMinima, Integer estrellas,
            Boolean tieneRestaurante, Boolean tieneGimnasio, Boolean tieneServicioHabitaciones) {
        return hotelRepository.buscarHotelesPorFiltros(region, puntuacionMediaMinima, estrellas, tieneRestaurante,
                tieneGimnasio, tieneServicioHabitaciones);
    }

    /**
     * Método que añade un hotel nuevo. Aplica validaciones según el número de
     * estrellas: un hotel de 5 estrellas debe tener gimnasio, restaurante y
     * servicio de habitaciones; uno de 4 estrellas restaurante y servicio de
     * habitaciones; uno de 3 estrellas restaurante obligatoriamente.
     *
     * @param hotelACrear Hotel que se quiere añadir
     * @return Hotel recién añadido
     * @throws RuntimeException         si el número de estrellas no está entre 1 y
     *                                  5
     * @throws IllegalArgumentException si no cumple los requisitos de servicios
     *                                  según sus estrellas
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Hotel crearHotel(Hotel hotelACrear) {

        if (hotelACrear.getEstrellas() < 1 || hotelACrear.getEstrellas() > 5) {
            throw new RuntimeException("El hotel tiene que tener un número de estrellas entre 1 y 5");
        }

        if (hotelACrear.getEstrellas() == 5) {
            if (!hotelACrear.getTieneGimnasio() || !hotelACrear.getTieneServicioHabitaciones()
                    || !hotelACrear.getTieneRestaurante()) {
                throw new IllegalArgumentException(
                        "Un hotel de 5 estrellas debe tener obligatoriamente gimnasio, servicio de habitaciones y restaurante.");
            }
        }

        if (hotelACrear.getEstrellas() == 4) {
            if (!hotelACrear.getTieneServicioHabitaciones() || !hotelACrear.getTieneRestaurante()) {
                throw new IllegalArgumentException(
                        "Un hotel de 4 estrellas debe tener obligatoriamente servicio de habitaciones y restaurante.");
            }
        }

        if (hotelACrear.getEstrellas() == 3) {
            if (!hotelACrear.getTieneRestaurante()) {
                throw new IllegalArgumentException("Un hotel de 3 estrellas debe tener obligatoriamente restaurante.");
            }
        }

        hotelACrear.setPuntuaciones(
                hotelACrear.getPuntuaciones() != null ? hotelACrear.getPuntuaciones() : new ArrayList<>());
        hotelACrear.calcularMedia();
        return hotelRepository.save(hotelACrear);
    }

    /**
     * Método para actualizar un hotel al completo.
     *
     * @param id              ID único del hotel que se quiere modificar
     * @param hotelModificado Hotel con los datos nuevos que sustituirán a los
     *                        antiguos
     * @return Hotel modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Hotel actualizarHotel(Long id, Hotel hotelModificado) {
        Hotel hotelAModificar = buscarPorId(id);

        hotelAModificar.setNombre(hotelModificado.getNombre());
        hotelAModificar.setDescripcion(hotelModificado.getDescripcion());
        hotelAModificar.setDireccion(hotelModificado.getDireccion());
        hotelAModificar.setRegion(hotelModificado.getRegion());

        if (hotelModificado.getPuntuaciones() != null) {
            hotelAModificar.setPuntuaciones(hotelModificado.getPuntuaciones());
            hotelAModificar.calcularMedia();
        }

        hotelAModificar.setEstrellas(hotelModificado.getEstrellas());
        hotelAModificar.setTieneRestaurante(hotelModificado.getTieneRestaurante());
        hotelAModificar.setTieneGimnasio(hotelModificado.getTieneGimnasio());
        hotelAModificar.setTieneServicioHabitaciones(hotelModificado.getTieneServicioHabitaciones());

        return hotelRepository.save(hotelAModificar);
    }

    /**
     * Método que actualiza parcialmente un hotel. Revisa si un dato no es
     * nulo y, en ese caso, lo actualiza.
     *
     * @param id              ID único del hotel que se quiere modificar
     * @param hotelModificado Hotel con los datos nuevos que sustituirán a los
     *                        antiguos
     * @return Hotel modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Hotel actualizarHotelParcialmente(Long id, Hotel hotelModificado) {
        Hotel hotelAModificar = buscarPorId(id);

        if (hotelModificado.getNombre() != null) {
            hotelAModificar.setNombre(hotelModificado.getNombre());
        }

        if (hotelModificado.getDescripcion() != null) {
            hotelAModificar.setDescripcion(hotelModificado.getDescripcion());
        }

        if (hotelModificado.getDireccion() != null) {
            hotelAModificar.setDireccion(hotelModificado.getDireccion());
        }

        if (hotelModificado.getRegion() != null) {
            hotelAModificar.setRegion(hotelModificado.getRegion());
        }

        if (hotelModificado.getPuntuaciones() != null) {
            hotelAModificar.setPuntuaciones(hotelModificado.getPuntuaciones());
            hotelAModificar.calcularMedia();
        }

        if (hotelModificado.getEstrellas() != null) {
            hotelAModificar.setEstrellas(hotelModificado.getEstrellas());
        }

        if (hotelModificado.getTieneRestaurante() != null) {
            hotelAModificar.setTieneRestaurante(hotelModificado.getTieneRestaurante());
        }

        if (hotelModificado.getTieneGimnasio() != null) {
            hotelAModificar.setTieneGimnasio(hotelModificado.getTieneGimnasio());
        }

        if (hotelModificado.getTieneServicioHabitaciones() != null) {
            hotelAModificar.setTieneServicioHabitaciones(hotelModificado.getTieneServicioHabitaciones());
        }

        return hotelRepository.save(hotelAModificar);
    }

    /**
     * Elimina un hotel a través de su ID si es que este existe, si no
     * lanza excepción.
     *
     * @param id ID único del hotel que se quiere eliminar
     * @throws RuntimeException si no se encuentra el hotel
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public void eliminarHotel(Long id) {
        Hotel hotel = buscarPorId(id);

        for (Habitacion habitacion : hotel.getHabitaciones()) {
            for (Reserva reserva : habitacion.getReservas()) {
                reserva.getHabitaciones().remove(habitacion);
                reservaRepository.save(reserva);
            }
            habitacion.getReservas().clear();
            habitacionRepository.save(habitacion);
        }

        hotelRepository.deleteById(id);
    }
}