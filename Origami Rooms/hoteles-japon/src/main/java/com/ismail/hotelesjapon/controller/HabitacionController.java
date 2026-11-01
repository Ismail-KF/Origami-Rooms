package com.ismail.hotelesjapon.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;
import com.ismail.hotelesjapon.service.HabitacionService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con las
 * {@link Habitacion}.
 * <p>
 * Expone endpoints bajo la ruta /api/habitaciones para realizar operaciones
 * de consulta, creación, actualización y eliminación de habitaciones.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see HabitacionService
 */
@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {
    @Autowired
    private HabitacionService habitacionService;

    /**
     * Endpoint que devuelve todas las habitaciones registradas.
     *
     * @return Lista de todas las habitaciones con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Habitacion>> mostrarTodas() {
        return ResponseEntity.ok(habitacionService.mostrarTodas());
    }

    /**
     * Endpoint que devuelve una habitación concreta buscada por su ID.
     *
     * @param id Identificador único de la habitación
     * @return Habitación encontrada con código 200
     * @throws RuntimeException si no se encuentra la habitación
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habitacionService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve una lista de habitaciones filtradas por uno
     * o varios parámetros opcionales. Los parámetros no incluidos en la
     * petición serán ignorados en la búsqueda.
     *
     * @param tipo            Tipo de habitación
     * @param capacidadMinima Capacidad mínima de la habitación
     * @param precioMaximo    Precio máximo por noche
     * @return Lista de habitaciones que concuerden con los filtros con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Habitacion>> buscarHabitaciones(
            @RequestParam(required = false) Tipo tipo,
            @RequestParam(required = false) Integer capacidadMinima,
            @RequestParam(required = false) Double precioMaximo) {
        return ResponseEntity.ok(habitacionService.buscarHabitaciones(tipo, capacidadMinima, precioMaximo));
    }

    /**
     * Endpoint que comprueba si una habitación está disponible para unas
     * fechas concretas.
     *
     * @param id           Identificador único de la habitación
     * @param fechaEntrada Fecha de entrada deseada en formato yyyy-MM-dd
     * @param fechaSalida  Fecha de salida deseada en formato yyyy-MM-dd
     * @return true si la habitación está disponible, false si no lo está, con
     *         código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<Boolean> estaDisponible(
            @PathVariable Long id,
            @RequestParam LocalDate fechaEntrada,
            @RequestParam LocalDate fechaSalida) {
        return ResponseEntity.ok(habitacionService.estaDisponible(id, fechaEntrada, fechaSalida));
    }

    /**
     * Endpoint que crea una nueva habitación asociándola a un establecimiento.
     *
     * @param habitacion        Habitación que se quiere crear
     * @param establecimientoId ID del establecimiento al que pertenece la
     *                          habitación
     * @return Habitación recién creada con código 201
     * @throws RuntimeException si falta algún dato obligatorio o no se encuentra
     *                          el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @PostMapping("/establecimiento/{establecimientoId}")
    public ResponseEntity<Habitacion> crearHabitacion(
            @RequestBody Habitacion habitacion,
            @PathVariable Long establecimientoId) {
        return ResponseEntity.status(201).body(habitacionService.crearHabitacion(habitacion, establecimientoId));
    }

    /**
     * Endpoint que actualiza una habitación al completo.
     *
     * @param id         Identificador único de la habitación que se quiere
     *                   modificar
     * @param habitacion Habitación con los datos nuevos que sustituirán a los
     *                   antiguos
     * @return Habitación modificada con código 200
     * @throws RuntimeException si no se encuentra la habitación
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        return ResponseEntity.ok(habitacionService.actualizarHabitacion(id, habitacion));
    }

    /**
     * Endpoint que actualiza parcialmente una habitación. Solo se actualizarán
     * los campos que se incluyan en la petición.
     *
     * @param id         Identificador único de la habitación que se quiere
     *                   modificar
     * @param habitacion Habitación con los datos nuevos que sustituirán a los
     *                   antiguos
     * @return Habitación modificada con código 200
     * @throws RuntimeException si no se encuentra la habitación
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacionParcialmente(@PathVariable Long id,
            @RequestBody Habitacion habitacion) {
        return ResponseEntity.ok(habitacionService.actualizarHabitacionParcialmente(id, habitacion));
    }

    /**
     * Endpoint que elimina una habitación a través de su ID.
     *
     * @param id Identificador único de la habitación que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra la habitación
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        habitacionService.eliminarHabitacion(id);
        return ResponseEntity.noContent().build();
    }
}