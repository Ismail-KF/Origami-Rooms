package com.ismail.hotelesjapon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.repository.ClienteRepository;
import com.ismail.hotelesjapon.service.ClienteService;

/**
 * Servicio que implementa los métodos creados en {@link ClienteService}.
 * 
 * @author Ismail
 * @version 1.0
 * @see EstablecimientoServiceImpl
 * @see HabitacionServiceImpl
 * @see HotelServiceImpl
 * @see ReservaServiceImpl
 * @see RyokanServiceImpl
 */
@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Método que lista todos los clientes guardados en la base de datos
     * 
     * @return Lista de todos los clientes existentes
     * @author Ismail
     * @since 1.0
     */
    @Override
    public List<Cliente> mostrarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();

        if (clientes.isEmpty()) {
            throw new RuntimeException("No se han encontrado clientes.");
        }

        return clientes;
    }

    /**
     * Método que busca un cliente concreto por su ID propio
     * 
     * @param id ID único del cliente
     * @return El cliente si se ha encontrado
     * @throws RuntimeException Si el cliente no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    /**
     * Método que busca un cliente concreto por su email personal
     * 
     * @param email email único del cliente
     * @return El cliente si se ha encontrado
     * @throws RuntimeException Si el cliente no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    /**
     * Método que busca un cliente concreto por su número de teléfono
     * 
     * @param telefono Número de teléfono del cliente
     * @return El cliente si se ha encontrado
     * @throws RuntimeException Si el cliente no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente buscarPorTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    /**
     * Método que busca un cliente concreto por su pasaporte
     * 
     * @param pasaporte Pasaporte del cliente
     * @return El cliente si se ha encontrado
     * @throws RuntimeException Si el cliente no se ha encontrado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente buscarPorPasaporte(String pasaporte) {
        return clienteRepository.findByPasaporte(pasaporte)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    /**
     * Método que añade un cliente nuevo. Si algún dato no está presente, es
     * inválido o se repite uno que deba ser único lanza un error.
     * 
     * @param clienteACrear Cliente que se quiere añadir
     * @return Cliente recién añadido
     * @throws RunTimeException si falta algún dato del cliente o se repite alguno
     *                          que deba ser único
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente crearCliente(Cliente clienteACrear) {
        List<Cliente> clientes = clienteRepository.findAll();

        if (clienteACrear.getNombre() == null || clienteACrear.getNombre().isEmpty()
                || clienteACrear.getNombre().matches(".*\\d.*")) {
            throw new RuntimeException("El cliente debe tener un nombre válido.");
        }

        if (clienteACrear.getApellido() == null || clienteACrear.getApellido().isEmpty()
                || clienteACrear.getApellido().matches(".*\\d.*")) {
            throw new RuntimeException("El cliente debe tener un apellido válido.");
        }

        if (clienteACrear.getEmail() == null || clienteACrear.getEmail().isEmpty()
                || !clienteACrear.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("El cliente debe tener un correo electrónico.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getEmail().equals(clienteACrear.getEmail())) {
                    throw new RuntimeException("Ya existe un cliente con ese correo electrónico.");
                }
            }
        }

        if (clienteACrear.getTelefono() == null || clienteACrear.getTelefono().isEmpty()
                || !clienteACrear.getTelefono().matches("^(?:(?:\\+|00)\\d{1,3}[\\s-]?)?[67]\\d{8}$")) {
            throw new RuntimeException("El cliente debe tener un número de teléfono válido.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getTelefono().equals(clienteACrear.getTelefono())) {
                    throw new RuntimeException("Ya existe un cliente con ese número de teléfono.");
                }
            }
        }

        if (clienteACrear.getPasaporte() == null || clienteACrear.getPasaporte().isEmpty()
                || !clienteACrear.getPasaporte().matches("^[a-zA-Z]{3}[0-9]{6}[a-zA-Z]?$")) {
            throw new RuntimeException("El cliente debe tener un pasaporte.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getPasaporte().equals(clienteACrear.getPasaporte())) {
                    throw new RuntimeException("Ya existe un cliente con ese pasaporte.");
                }
            }
        }

        if (clienteACrear.getNacionalidad() == null || clienteACrear.getNacionalidad().isEmpty()
                || clienteACrear.getNacionalidad().contains("[0-9]")) {
            throw new RuntimeException("El cliente debe tener una nacionalidad válida.");
        }

        return clienteRepository.save(clienteACrear);
    }

    /**
     * Actualiza completamente un cliente
     * 
     * @param id                ID del cliente que se quiere modificar
     * @param clienteModificado Cliente con los nuevos datos que sustituirán a los
     *                          antiguos
     * @return Cliente modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente actualizarCliente(Long id, Cliente clienteModificado) {
        Cliente clienteAModificar = buscarPorId(id);

        clienteAModificar.setNombre(clienteModificado.getNombre());
        clienteAModificar.setApellido(clienteModificado.getApellido());
        clienteAModificar.setEmail(clienteModificado.getEmail());
        clienteAModificar.setTelefono(clienteModificado.getTelefono());
        clienteAModificar.setPasaporte(clienteModificado.getPasaporte());
        clienteAModificar.setNacionalidad(clienteModificado.getNacionalidad());

        return clienteRepository.save(clienteAModificar);
    }

    /**
     * Actualiza parcialmente un cliente. Revisa si un dato no es nulo y, en ese
     * caso, lo actualiza.
     * 
     * @param id                ID del cliente que se quiere modificar
     * @param clienteModificado Cliente con los nuevos datos que sustituirán a los
     *                          antiguos
     * @return Cliente modificado
     * @author Ismail
     * @since 1.0
     */
    @Override
    public Cliente actualizarClienteParcialmente(Long id, Cliente clienteModificado) {
        Cliente clienteAModificar = buscarPorId(id);

        if (clienteModificado.getNombre() != null) {
            clienteAModificar.setNombre(clienteModificado.getNombre());
        }

        if (clienteModificado.getApellido() != null) {
            clienteAModificar.setApellido(clienteModificado.getApellido());
        }

        if (clienteModificado.getEmail() != null) {
            clienteAModificar.setEmail(clienteModificado.getEmail());
        }

        if (clienteModificado.getTelefono() != null) {
            clienteAModificar.setTelefono(clienteModificado.getTelefono());
        }

        if (clienteModificado.getPasaporte() != null) {
            clienteAModificar.setPasaporte(clienteModificado.getPasaporte());
        }

        if (clienteModificado.getNacionalidad() != null) {
            clienteAModificar.setNacionalidad(clienteModificado.getNacionalidad());
        }

        return clienteRepository.save(clienteAModificar);
    }

    /**
     * Elimina un cliente a través de su ID si es que este existe, si no lanza
     * excepción.
     * 
     * @param id ID único del cliente
     * @throws RunTimeException si no existe el cliente que se quiere eliminar
     * @author Ismail
     * @since 1.0
     */
    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("No se ha encontrado ningún cliente con el id: " + id);
        }

        clienteRepository.deleteById(id);
    }
}