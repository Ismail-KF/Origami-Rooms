package com.ismail.hotelesjapon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Ryokan;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.repository.ReservaRepository;
import com.ismail.hotelesjapon.repository.RyokanRepository;
import com.ismail.hotelesjapon.service.RyokanService;

import jakarta.transaction.Transactional;

/**
 * Servicio que implementa los métodos creados en {@link RyokanService}
 *
 * @author Ismail
 * @version 1.0
 * @see ClienteServiceImpl
 * @see EstablecimientoServiceImpl
 * @see HabitacionServiceImpl
 * @see HotelServiceImpl
 * @see ReservaServiceImpl
 */
@Service
public class RyokanServiceImpl implements RyokanService {
    @Autowired
    private RyokanRepository ryokanRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Método que muestra todos los ryokan registrados.
     *
     * @return Lista de todos los ryokan registrados
     * @throws RuntimeException si no hay ryokan registrados
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Ryokan> mostrarTodos() {
        List<Ryokan> ryokan = ryokanRepository.findAll();

        if (ryokan.isEmpty()) {
            throw new RuntimeException("No hay ryokan registrados.");
        }

        return ryokan;
    }

    /**
     * Método para buscar un ryokan concreto por ID.
     *
     * @param id Identificador único del ryokan
     * @return Ryokan si se ha encontrado
     * @throws RuntimeException si no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Ryokan buscarPorId(Long id) {
        return ryokanRepository.findById(id).orElseThrow(() -> new RuntimeException("Ryokan no encontrado."));
    }

    /**
     * Método que sirve para buscar ryokan a través de diferentes filtros
     * como la región, la puntuación media, las estrellas o los servicios
     * disponibles.
     *
     * @param region                Región de Japón donde se encuentra el ryokan
     * @param puntuacionMediaMinima Puntuación media mínima del ryokan
     * @param estrellas             Número de estrellas del ryokan
     * @param tieneOnsen            Si el ryokan tiene onsen
     * @param tieneKimono           Si el ryokan tiene servicio de kimono
     * @param tieneJardin           Si el ryokan tiene jardín tradicional
     * @return Lista de ryokan que concuerden con los filtros
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Ryokan> buscarRyokan(Region region, Double puntuacionMediaMinima, Integer estrellas, Boolean tieneOnsen,
            Boolean tieneKimono, Boolean tieneJardin) {
        return ryokanRepository.buscarRyokanPorFiltros(region, puntuacionMediaMinima, estrellas, tieneOnsen,
                tieneKimono, tieneJardin);
    }

    /**
     * Método que añade un ryokan nuevo. Aplica validaciones según el número de
     * estrellas: un ryokan de 5 estrellas debe tener onsen, kimono y jardín;
     * uno de 4 estrellas kimono y jardín; uno de 3 estrellas kimono
     * obligatoriamente.
     *
     * @param ryokanACrear Ryokan que se quiere añadir
     * @return Ryokan recién añadido
     * @throws RuntimeException         si el número de estrellas no está entre 1 y
     *                                  5
     * @throws IllegalArgumentException si no cumple los requisitos de servicios
     *                                  según sus estrellas
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Ryokan crearRyokan(Ryokan ryokanACrear) {

        if (ryokanACrear.getEstrellas() < 1 || ryokanACrear.getEstrellas() > 5) {
            throw new RuntimeException("El ryokan tiene que tener un número de estrellas entre 1 y 5");
        }

        if (ryokanACrear.getEstrellas() == 5) {
            if (!ryokanACrear.getTieneOnsen() || !ryokanACrear.getTieneKimono() || !ryokanACrear.getTieneJardin()) {
                throw new IllegalArgumentException(
                        "Un ryokan de 5 estrellas debe tener obligatoriamente un onsen, servicio de kimono y jardín.");
            }
        }

        if (ryokanACrear.getEstrellas() == 4) {
            if (!ryokanACrear.getTieneKimono() || !ryokanACrear.getTieneJardin()) {
                throw new IllegalArgumentException(
                        "Un ryokan de 4 estrellas debe tener obligatoriamente servicio de kimono y jardín.");
            }
        }

        if (ryokanACrear.getEstrellas() == 3) {
            if (!ryokanACrear.getTieneKimono()) {
                throw new IllegalArgumentException(
                        "Un ryokan de 3 estrellas debe tener obligatoriamente servicio de kimono.");
            }
        }

        ryokanACrear.setPuntuaciones(
                ryokanACrear.getPuntuaciones() != null ? ryokanACrear.getPuntuaciones() : new ArrayList<>());
        ryokanACrear.calcularMedia();
        return ryokanRepository.save(ryokanACrear);
    }

    /**
     * Método para actualizar un ryokan al completo.
     *
     * @param id               ID único del ryokan que se quiere modificar
     * @param ryokanModificado Ryokan con los datos nuevos que sustituirán a los
     *                         antiguos
     * @return Ryokan modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Ryokan actualizarRyokan(Long id, Ryokan ryokanModificado) {
        Ryokan ryokanAModificar = buscarPorId(id);

        ryokanAModificar.setNombre(ryokanModificado.getNombre());
        ryokanAModificar.setDescripcion(ryokanModificado.getDescripcion());
        ryokanAModificar.setDireccion(ryokanModificado.getDireccion());
        ryokanAModificar.setRegion(ryokanModificado.getRegion());

        if (ryokanModificado.getPuntuaciones() != null) {
            ryokanAModificar.setPuntuaciones(ryokanModificado.getPuntuaciones());
            ryokanAModificar.calcularMedia();
        }

        ryokanAModificar.setEstrellas(ryokanModificado.getEstrellas());
        ryokanAModificar.setTieneJardin(ryokanModificado.getTieneJardin());
        ryokanAModificar.setTieneKimono(ryokanModificado.getTieneKimono());
        ryokanAModificar.setTieneOnsen(ryokanModificado.getTieneOnsen());

        return ryokanRepository.save(ryokanAModificar);
    }

    /**
     * Método que actualiza parcialmente un ryokan. Revisa si un dato no es
     * nulo y, en ese caso, lo actualiza.
     *
     * @param id               ID único del ryokan que se quiere modificar
     * @param ryokanModificado Ryokan con los datos nuevos que sustituirán a los
     *                         antiguos
     * @return Ryokan modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Ryokan actualizarRyokanParcialmente(Long id, Ryokan ryokanModificado) {
        Ryokan ryokanAModificar = buscarPorId(id);

        if (ryokanModificado.getNombre() != null) {
            ryokanAModificar.setNombre(ryokanModificado.getNombre());
        }

        if (ryokanModificado.getDescripcion() != null) {
            ryokanAModificar.setDescripcion(ryokanModificado.getDescripcion());
        }

        if (ryokanModificado.getDireccion() != null) {
            ryokanAModificar.setDireccion(ryokanModificado.getDireccion());
        }

        if (ryokanModificado.getRegion() != null) {
            ryokanAModificar.setRegion(ryokanModificado.getRegion());
        }

        if (ryokanModificado.getPuntuaciones() != null) {
            ryokanAModificar.setPuntuaciones(ryokanModificado.getPuntuaciones());
            ryokanAModificar.calcularMedia();
        }

        if (ryokanModificado.getEstrellas() != null) {
            ryokanAModificar.setEstrellas(ryokanModificado.getEstrellas());
        }

        if (ryokanModificado.getTieneJardin() != null) {
            ryokanAModificar.setTieneJardin(ryokanModificado.getTieneJardin());
        }

        if (ryokanModificado.getTieneKimono() != null) {
            ryokanAModificar.setTieneKimono(ryokanModificado.getTieneKimono());
        }

        if (ryokanModificado.getTieneOnsen() != null) {
            ryokanAModificar.setTieneOnsen(ryokanModificado.getTieneOnsen());
        }

        return ryokanRepository.save(ryokanAModificar);
    }

    /**
     * Elimina un ryokan a través de su ID si es que este existe, si no
     * lanza excepción.
     *
     * @param id ID único del ryokan que se quiere eliminar
     * @throws RuntimeException si no se encuentra el ryokan
     * @author Ismail
     * @since 1.0
     */
    @Transactional
    @Override
    public void eliminarRyokan(Long id) {
        Ryokan ryokan = buscarPorId(id);

        for (Habitacion habitacion : ryokan.getHabitaciones()) {
            for (Reserva reserva : habitacion.getReservas()) {
                reserva.getHabitaciones().remove(habitacion);
                reservaRepository.save(reserva);
            }
            habitacion.getReservas().clear();
            habitacionRepository.save(habitacion);
        }

        ryokanRepository.deleteById(id);
    }
}