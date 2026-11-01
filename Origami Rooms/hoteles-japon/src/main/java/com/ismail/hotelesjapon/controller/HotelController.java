package com.ismail.hotelesjapon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Hotel;
import com.ismail.hotelesjapon.service.HotelService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los
 * {@link Hotel}.
 * <p>
 * Expone endpoints bajo la ruta /api/v1/hoteles para realizar operaciones
 * de consulta, creación, actualización y eliminación de hoteles.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see HotelService
 */
@RestController
@RequestMapping("/api/v1/hoteles")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    /**
     * Endpoint que devuelve todos los hoteles registrados.
     *
     * @return Lista de todos los hoteles con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> mostrarTodos() {
        return ResponseEntity.ok(hotelService.mostrarTodos());
    }

    /**
     * Endpoint que devuelve un hotel concreto buscado por su ID.
     *
     * @param id Identificador único del hotel
     * @return Hotel encontrado con código 200
     * @throws RuntimeException si no se encuentra el hotel
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve una lista de hoteles filtrados por uno o varios
     * parámetros opcionales. Los parámetros no incluidos en la petición
     * serán ignorados en la búsqueda.
     *
     * @param region                    Región de Japón donde se encuentra el hotel
     * @param puntuacionMediaMinima     Puntuación media mínima del hotel
     * @param estrellas                 Número de estrellas del hotel
     * @param tieneRestaurante          Si el hotel tiene restaurante
     * @param tieneGimnasio             Si el hotel tiene gimnasio
     * @param tieneServicioHabitaciones Si el hotel tiene servicio de habitaciones
     * @return Lista de hoteles que concuerden con los filtros con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Hotel>> buscarHoteles(@RequestParam(required = false) Region region,
            @RequestParam(required = false) Double puntuacionMediaMinima,
            @RequestParam(required = false) Integer estrellas, @RequestParam(required = false) Boolean tieneRestaurante,
            @RequestParam(required = false) Boolean tieneGimnasio,
            @RequestParam(required = false) Boolean tieneServicioHabitaciones) {
        return ResponseEntity.ok(hotelService.buscarHoteles(region, puntuacionMediaMinima, estrellas, tieneRestaurante,
                tieneGimnasio, tieneServicioHabitaciones));
    }

    /**
     * Endpoint que crea un nuevo hotel. Aplica validaciones según el número
     * de estrellas para comprobar que tiene los servicios obligatorios.
     *
     * @param hotel Hotel que se quiere crear
     * @return Hotel recién creado con código 201
     * @throws RuntimeException         si el número de estrellas no está entre 1 y
     *                                  5
     * @throws IllegalArgumentException si no cumple los requisitos de servicios
     *                                  según sus estrellas
     * @author Ismail
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(201).body(hotelService.crearHotel(hotel));
    }

    /**
     * Endpoint que actualiza un hotel al completo.
     *
     * @param id    Identificador único del hotel que se quiere modificar
     * @param hotel Hotel con los datos nuevos que sustituirán a los antiguos
     * @return Hotel modificado con código 200
     * @throws RuntimeException si no se encuentra el hotel
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.actualizarHotel(id, hotel));
    }

    /**
     * Endpoint que actualiza parcialmente un hotel. Solo se actualizarán
     * los campos que se incluyan en la petición.
     *
     * @param id    Identificador único del hotel que se quiere modificar
     * @param hotel Hotel con los datos nuevos que sustituirán a los antiguos
     * @return Hotel modificado con código 200
     * @throws RuntimeException si no se encuentra el hotel
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotelParcialmente(@PathVariable Long id, @RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.actualizarHotelParcialmente(id, hotel));
    }

    /**
     * Endpoint que elimina un hotel a través de su ID.
     *
     * @param id Identificador único del hotel que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra el hotel
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHotel(@PathVariable Long id) {
        hotelService.eliminarHotel(id);
        return ResponseEntity.noContent().build();
    }
}