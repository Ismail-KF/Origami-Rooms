package com.ismail.hotelesjapon.service;

import java.util.List;
import com.ismail.hotelesjapon.entity.Cliente;

public interface ClienteService {
    List<Cliente> mostrarTodos();

    Cliente buscarPorId(Long id);

    Cliente buscarPorEmail(String email);

    Cliente buscarPorTelefono(String telefono);

    Cliente buscarPorPasaporte(String pasaporte);

    Cliente crearCliente(Cliente cliente);

    Cliente actualizarCliente(Long id, Cliente cliente);

    Cliente actualizarClienteParcialmente(Long id, Cliente cliente);

    void eliminarCliente(Long id);
}