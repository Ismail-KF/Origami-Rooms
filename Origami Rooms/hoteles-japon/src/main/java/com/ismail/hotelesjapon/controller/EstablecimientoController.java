package com.ismail.hotelesjapon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.service.EstablecimientoService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los
 * {@link Establecimiento}.
 * <p>
 * Expone endpoints bajo la ruta /api/establecimientos para realizar operaciones
 * de consulta, creación, actualización y eliminación de establecimientos.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see EstablecimientoService
 */
@RestController
@RequestMapping("/api/v1/establecimientos")
public class EstablecimientoController {
    @Autowired
    private EstablecimientoService establecimientoService;

    /**
     * Endpoint que devuelve todos los establecimientos registrados.
     *
     * @return Lista de todos los establecimientos con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Establecimiento>> mostrarTodos() {
        return ResponseEntity.ok(establecimientoService.mostrarTodos());
    }

    /**
     * Endpoint que devuelve un establecimiento concreto buscado por su ID.
     *
     * @param id Identificador único del establecimiento
     * @return Establecimiento encontrado con código 200
     * @throws RuntimeException si no se encuentra el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(establecimientoService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve una lista de establecimientos filtrados por uno
     * o varios parámetros opcionales. Los parámetros no incluidos en la
     * petición serán ignorados en la búsqueda.
     *
     * @param nombre                Nombre del establecimiento
     * @param direccion             Dirección del establecimiento
     * @param region                Región de Japón donde se encuentra
     * @param puntuacionMediaMinima Puntuación media mínima del establecimiento
     * @return Lista de establecimientos que concuerden con los filtros con código
     *         200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Establecimiento>> buscarEstablecimientos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String direccion,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) Double puntuacionMediaMinima) {
        return ResponseEntity
                .ok(establecimientoService.buscarEstablecimientos(nombre, direccion, region, puntuacionMediaMinima));
    }

    /**
     * Endpoint que crea un nuevo establecimiento.
     *
     * @param establecimiento Establecimiento que se quiere crear
     * @return Establecimiento recién creado con código 201
     * @throws RuntimeException si falta algún dato obligatorio
     * @author Ismail
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<Establecimiento> crearEstablecimiento(@RequestBody Establecimiento establecimiento) {
        return ResponseEntity.status(201).body(establecimientoService.crearEstablecimiento(establecimiento));
    }

    /**
     * Endpoint que actualiza un establecimiento al completo.
     *
     * @param id              Identificador único del establecimiento que se quiere
     *                        modificar
     * @param establecimiento Establecimiento con los datos nuevos que sustituirán a
     *                        los antiguos
     * @return Establecimiento modificado con código 200
     * @throws RuntimeException si no se encuentra el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> actualizarEstablecimiento(@PathVariable Long id,
            @RequestBody Establecimiento establecimiento) {
        return ResponseEntity.ok(establecimientoService.actualizarEstablecimiento(id, establecimiento));
    }

    /**
     * Endpoint que actualiza parcialmente un establecimiento. Solo se actualizarán
     * los campos que se incluyan en la petición.
     *
     * @param id              Identificador único del establecimiento que se quiere
     *                        modificar
     * @param establecimiento Establecimiento con los datos nuevos que sustituirán a
     *                        los antiguos
     * @return Establecimiento modificado con código 200
     * @throws RuntimeException si no se encuentra el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Establecimiento> actualizarEstablecimientoParcialmente(@PathVariable Long id,
            @RequestBody Establecimiento establecimiento) {
        return ResponseEntity.ok(establecimientoService.actualizarEstablecimientoParcialmente(id, establecimiento));
    }

    /**
     * Endpoint que elimina un establecimiento a través de su ID.
     *
     * @param id Identificador único del establecimiento que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra el establecimiento
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstablecimiento(@PathVariable Long id) {
        establecimientoService.eliminarEstablecimiento(id);
        return ResponseEntity.noContent().build();
    }
}