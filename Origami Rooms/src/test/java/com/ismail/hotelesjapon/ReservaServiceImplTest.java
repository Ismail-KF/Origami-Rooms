package com.ismail.hotelesjapon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ismail.hotelesjapon.entity.Cliente;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;
import com.ismail.hotelesjapon.repository.ClienteRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.repository.ReservaRepository;
import com.ismail.hotelesjapon.service.HabitacionService;
import com.ismail.hotelesjapon.service.impl.ReservaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HabitacionService habitacionService;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Reserva reserva;
    private Cliente cliente;
    private Habitacion habitacion;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Carlos", "García", "carlos@email.com", "612345678", "ESP123456");
        habitacion = new Habitacion("101", Tipo.DOBLE, 2, 150.0);
        habitacion.setReservas(new ArrayList<>());
        reserva = new Reserva(
                LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 15),
                Estado.PENDIENTE, 0.0);
        reserva.setHabitaciones(List.of(habitacion));
        reserva.setCliente(cliente);
    }

    @Test
    void buscarPorId_devuelveReserva_cuandoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        Reserva resultado = reservaService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(Estado.PENDIENTE, resultado.getEstadoReserva());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> reservaService.buscarPorId(1L));
    }

    @Test
    void calcularPrecioTotal_devuelvePrecioCorrecto() {
        double precio = reservaService.calcularPrecioTotal(reserva);
        // 5 dias * 150.0 por noche = 750.0
        assertEquals(750.0, precio);
    }

    @Test
    void crearReserva_creaCorrectamente_cuandoDatosValidos() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(habitacionService.estaDisponible(1L,
                reserva.getFechaEntrada(), reserva.getFechaSalida())).thenReturn(true);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva resultado = reservaService.crearReserva(reserva, 1L, List.of(1L));
        assertNotNull(resultado);
        assertEquals(Estado.PENDIENTE, resultado.getEstadoReserva());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void crearReserva_lanzaExcepcion_cuandoClienteNoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> reservaService.crearReserva(reserva, 1L, List.of(1L)));
    }

    @Test
    void crearReserva_lanzaExcepcion_cuandoHabitacionNoDisponible() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(habitacionService.estaDisponible(1L,
                reserva.getFechaEntrada(), reserva.getFechaSalida())).thenReturn(false);
        assertThrows(RuntimeException.class,
                () -> reservaService.crearReserva(reserva, 1L, List.of(1L)));
    }

    @Test
    void eliminarReserva_eliminaCorrectamente_cuandoExiste() {
        when(reservaRepository.existsById(1L)).thenReturn(true);
        reservaService.eliminarReserva(1L);
        verify(reservaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarReserva_lanzaExcepcion_cuandoNoExiste() {
        when(reservaRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> reservaService.eliminarReserva(1L));
    }
}