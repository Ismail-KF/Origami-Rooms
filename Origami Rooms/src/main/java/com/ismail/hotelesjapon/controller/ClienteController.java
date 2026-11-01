package com.ismail.hotelesjapon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.service.ClienteService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los
 * {@link Cliente}.
 * <p>
 * Expone endpoints bajo la ruta /api/clientes para realizar operaciones
 * de consulta, creación, actualización y eliminación de clientes.
 * </p>
 *
 * @author Ismail
 * @version 1.0
 * @see ClienteService
 */
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Endpoint que devuelve todos los clientes registrados.
     *
     * @return Lista de todos los clientes con código 200
     * @author Ismail
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> mostrarTodos() {
        return ResponseEntity.ok(clienteService.mostrarTodos());
    }

    /**
     * Endpoint que devuelve un cliente concreto buscado por su ID.
     *
     * @param id Identificador único del cliente
     * @return Cliente encontrado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> mostrarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    /**
     * Endpoint que devuelve un cliente concreto buscado por su email.
     *
     * @param email Correo electrónico del cliente
     * @return Cliente encontrado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> mostrarClientePorEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.buscarPorEmail(email));
    }

    /**
     * Endpoint que devuelve un cliente concreto buscado por su número de teléfono.
     *
     * @param telefono Número de teléfono del cliente
     * @return Cliente encontrado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Cliente> mostrarClientePorTelefono(@PathVariable String telefono) {
        return ResponseEntity.ok(clienteService.buscarPorTelefono(telefono));
    }

    /**
     * Endpoint que devuelve un cliente concreto buscado por su pasaporte.
     *
     * @param pasaporte Número de pasaporte del cliente
     * @return Cliente encontrado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @GetMapping("/pasaporte/{pasaporte}")
    public ResponseEntity<Cliente> mostrarClientePorPasaporte(@PathVariable String pasaporte) {
        return ResponseEntity.ok(clienteService.buscarPorPasaporte(pasaporte));
    }

    /**
     * Endpoint que crea un nuevo cliente.
     *
     * @param cliente Cliente que se quiere crear
     * @return Cliente recién creado con código 201
     * @throws RuntimeException si falta algún dato obligatorio
     * @author Ismail
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(201).body(clienteService.crearCliente(cliente));
    }

    /**
     * Endpoint que actualiza un cliente al completo.
     *
     * @param id      Identificador único del cliente que se quiere modificar
     * @param cliente Cliente con los datos nuevos que sustituirán a los antiguos
     * @return Cliente modificado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente));
    }

    /**
     * Endpoint que actualiza parcialmente un cliente. Solo se actualizarán
     * los campos que se incluyan en la petición.
     *
     * @param id      Identificador único del cliente que se quiere modificar
     * @param cliente Cliente con los datos nuevos que sustituirán a los antiguos
     * @return Cliente modificado con código 200
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> actualizarClienteParcialmente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizarClienteParcialmente(id, cliente));
    }

    /**
     * Endpoint que elimina un cliente a través de su ID.
     *
     * @param id Identificador único del cliente que se quiere eliminar
     * @return Respuesta vacía con código 204
     * @throws RuntimeException si no se encuentra el cliente
     * @author Ismail
     * @since 1.0
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}