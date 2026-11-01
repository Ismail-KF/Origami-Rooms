package com.ismail.hotelesjapon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.repository.ClienteRepository;
import com.ismail.hotelesjapon.service.impl.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Carlos", "García", "carlos@email.com", "612345678", "ESP123456");
        cliente.setNacionalidad("Española");
    }

    @Test
    void mostrarTodos_devuelveLista_cuandoHayClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> resultado = clienteService.mostrarTodos();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void mostrarTodos_lanzaExcepcion_cuandoNoHayClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> clienteService.mostrarTodos());
    }

    @Test
    void buscarPorId_devuelveCliente_cuandoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Cliente resultado = clienteService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clienteService.buscarPorId(1L));
    }

    @Test
    void buscarPorEmail_devuelveCliente_cuandoExiste() {
        when(clienteRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(cliente));
        Cliente resultado = clienteService.buscarPorEmail("carlos@email.com");
        assertNotNull(resultado);
        assertEquals("carlos@email.com", resultado.getEmail());
    }

    @Test
    void buscarPorEmail_lanzaExcepcion_cuandoNoExiste() {
        when(clienteRepository.findByEmail("noexiste@email.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clienteService.buscarPorEmail("noexiste@email.com"));
    }

    @Test
    void crearCliente_guardaYDevuelveCliente() {
        when(clienteRepository.findAll()).thenReturn(List.of());
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente resultado = clienteService.crearCliente(cliente);
        assertNotNull(resultado);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void eliminarCliente_eliminaCorrectamente_cuandoExiste() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        clienteService.eliminarCliente(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarCliente_lanzaExcepcion_cuandoNoExiste() {
        when(clienteRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> clienteService.eliminarCliente(1L));
    }
}