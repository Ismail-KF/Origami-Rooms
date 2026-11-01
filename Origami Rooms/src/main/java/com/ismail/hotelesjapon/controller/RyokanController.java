package com.ismail.hotelesjapon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Ryokan;
import com.ismail.hotelesjapon.service.RyokanService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los
 * {@link Ryokan}.
 * <p>
 * Expone endpoints bajo la ruta /api/v1/ryokan para realizar operaciones
 * de consulta, creación, actualización y eliminación de ryokan.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see RyokanService
 */
@RestController
@RequestMapping("/api/v1/ryokan")
public class RyokanController {
    @Autowired
    private RyokanService ryokanService;

    /**
     * Endpoint que devuelve todos los ryokan registrados.
     *
     * @return Lista de todos los ryokan con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Ryokan>> mostrarTodos() {
        return ResponseEntity.ok(ryokanService.mostrarTodos());
    }

    /**
     * Endpoint que devuelve un ryokan concreto buscado por su ID.
     *
     * @param id Identificador único del ryokan
     * @return Ryokan encontrado con código 200
     * @throws RuntimeException si no se encuentra el ryokan
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ryokan> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ryokanService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve una lista de ryokan filtrados por uno o varios
     * parámetros opcionales. Los parámetros no incluidos en la petición
     * serán ignorados en la búsqueda.
     *
     * @param region                Región de Japón donde se encuentra el ryokan
     * @param puntuacionMediaMinima Puntuación media mínima del ryokan
     * @param estrellas             Número de estrellas del ryokan
     * @param tieneOnsen            Si el ryokan tiene onsen
     * @param tieneKimono           Si el ryokan tiene servicio de kimono
     * @param tieneJardin           Si el ryokan tiene jardín tradicional
     * @return Lista de ryokan que concuerden con los filtros con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Ryokan>> buscarRyokan(@RequestParam(required = false) Region region,
            @RequestParam(required = false) Double puntuacionMediaMinima,
            @RequestParam(required = false) Integer estrellas, @RequestParam(required = false) Boolean tieneOnsen,
            @RequestParam(required = false) Boolean tieneKimono, @RequestParam(required = false) Boolean tieneJardin) {
        return ResponseEntity.ok(ryokanService.buscarRyokan(region, puntuacionMediaMinima, estrellas, tieneOnsen,
                tieneKimono, tieneJardin));
    }

    /**
     * Endpoint que crea un nuevo ryokan. Aplica validaciones según el número
     * de estrellas para comprobar que tiene los servicios obligatorios.
     *
     * @param ryokan Ryokan que se quiere crear
     * @return Ryokan recién creado con código 201
     * @throws RuntimeException         si el número de estrellas no está entre 1 y
     *                                  5
     * @throws IllegalArgumentException si no cumple los requisitos de servicios
     *                                  según sus estrellas
     * @author Ismail
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<Ryokan> crearRyokan(@RequestBody Ryokan ryokan) {
        return ResponseEntity.status(201).body(ryokanService.crearRyokan(ryokan));
    }

    /**
     * Endpoint que actualiza un ryokan al completo.
     *
     * @param id     Identificador único del ryokan que se quiere modificar
     * @param ryokan Ryokan con los datos nuevos que sustituirán a los antiguos
     * @return Ryokan modificado con código 200
     * @throws RuntimeException si no se encuentra el ryokan
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ryokan> actualizarRyokan(@PathVariable Long id, @RequestBody Ryokan ryokan) {
        return ResponseEntity.ok(ryokanService.actualizarRyokan(id, ryokan));
    }

    /**
     * Endpoint que actualiza parcialmente un ryokan. Solo se actualizarán
     * los campos que se incluyan en la petición.
     *
     * @param id     Identificador único del ryokan que se quiere modificar
     * @param ryokan Ryokan con los datos nuevos que sustituirán a los antiguos
     * @return Ryokan modificado con código 200
     * @throws RuntimeException si no se encuentra el ryokan
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Ryokan> actualizarRyokanParcialmente(@PathVariable Long id, @RequestBody Ryokan ryokan) {
        return ResponseEntity.ok(ryokanService.actualizarRyokanParcialmente(id, ryokan));
    }

    /**
     * Endpoint que elimina un ryokan a través de su ID.
     *
     * @param id Identificador único del ryokan que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra el ryokan
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRyokan(@PathVariable Long id) {
        ryokanService.eliminarRyokan(id);
        return ResponseEntity.noContent().build();
    }
}