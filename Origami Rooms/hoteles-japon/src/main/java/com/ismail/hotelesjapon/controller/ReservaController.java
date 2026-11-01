package com.ismail.hotelesjapon.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;
import com.ismail.hotelesjapon.service.ReservaService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con las
 * {@link Reserva}.
 * <p>
 * Expone endpoints bajo la ruta /api/v1/reservas para realizar operaciones
 * de consulta, creación, actualización y eliminación de reservas.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see ReservaService
 */
@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    /**
     * Endpoint que devuelve todas las reservas registradas.
     *
     * @return Lista de todas las reservas con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Reserva>> mostrarTodas() {
        return ResponseEntity.ok(reservaService.mostrarTodas());
    }

    /**
     * Endpoint que devuelve una reserva concreta buscada por su ID.
     *
     * @param id Identificador único de la reserva
     * @return Reserva encontrada con código 200
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve una lista de reservas filtradas por uno o varios
     * parámetros opcionales. Los parámetros no incluidos en la petición
     * serán ignorados en la búsqueda.
     *
     * @param clienteId   ID del cliente que realizó la reserva
     * @param estado      Estado de la reserva
     * @param fechaInicio Fecha de entrada de la reserva
     * @param fechaFin    Fecha de salida de la reserva
     * @return Lista de reservas que concuerden con los filtros con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Reserva>> buscarReservas(@RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Estado estado, @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin) {
        return ResponseEntity.ok(reservaService.buscarReservas(clienteId, estado, fechaInicio, fechaFin));
    }

    /**
     * Endpoint que crea una nueva reserva asociándola a un cliente y a una
     * o varias habitaciones comprobando su disponibilidad para las fechas
     * indicadas.
     *
     * @param reserva        Reserva que se quiere crear
     * @param clienteId      ID del cliente que realiza la reserva
     * @param habitacionesId Lista de IDs de las habitaciones a reservar
     * @return Reserva recién creada con estado PENDIENTE y precio calculado, con
     *         código 201
     * @throws RuntimeException si el cliente no existe, alguna habitación no existe
     *                          o no está disponible para esas fechas
     * @author Ismail
     * @since 1.0
     */
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Reserva> crearReserva(
            @RequestBody Reserva reserva,
            @PathVariable Long clienteId,
            @RequestParam List<Long> habitacionesId) {
        return ResponseEntity.status(201).body(reservaService.crearReserva(reserva, clienteId, habitacionesId));
    }

    /**
     * Endpoint que actualiza una reserva al completo. Si el nuevo estado
     * es CANCELADA libera automáticamente las habitaciones asociadas.
     *
     * @param id      Identificador único de la reserva que se quiere modificar
     * @param reserva Reserva con los datos nuevos que sustituirán a los antiguos
     * @return Reserva modificada con código 200
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.actualizarReserva(id, reserva));
    }

    /**
     * Endpoint que actualiza parcialmente una reserva. Solo se actualizarán
     * los campos que se incluyan en la petición. Si el nuevo estado es CANCELADA
     * libera automáticamente las habitaciones asociadas.
     *
     * @param id      Identificador único de la reserva que se quiere modificar
     * @param reserva Reserva con los datos nuevos que sustituirán a los antiguos
     * @return Reserva modificada con código 200
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReservaParcialmente(@PathVariable Long id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.actualizarReservaParcialmente(id, reserva));
    }

    /**
     * Endpoint que calcula el precio total de una reserva existente sumando
     * el precio por noche de todas sus habitaciones multiplicado por los días
     * de estancia.
     *
     * @param id Identificador único de la reserva
     * @return Precio total de la reserva con código 200
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}/precio")
    public ResponseEntity<Double> calcularPrecioTotal(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(reservaService.calcularPrecioTotal(reserva));
    }

    /**
     * Endpoint que elimina una reserva a través de su ID.
     *
     * @param id Identificador único de la reserva que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra la reserva
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}