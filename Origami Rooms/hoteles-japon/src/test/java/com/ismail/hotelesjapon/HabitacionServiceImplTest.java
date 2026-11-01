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
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;
import com.ismail.hotelesjapon.repository.EstablecimientoRepository;
import com.ismail.hotelesjapon.repository.HabitacionRepository;
import com.ismail.hotelesjapon.service.impl.HabitacionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceImplTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private EstablecimientoRepository establecimientoRepository;

    @InjectMocks
    private HabitacionServiceImpl habitacionService;

    private Habitacion habitacion;

    @BeforeEach
    void setUp() {
        habitacion = new Habitacion("101", Tipo.DOBLE, 2, 150.0);
        habitacion.setReservas(new ArrayList<>());
    }

    @Test
    void buscarPorId_devuelveHabitacion_cuandoExiste() {
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        Habitacion resultado = habitacionService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("101", resultado.getNumeroHabitacion());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(habitacionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> habitacionService.buscarPorId(1L));
    }

    @Test
    void crearHabitacion_guardaYDevuelve_cuandoDatosCorrectos() {
        Establecimiento establecimiento = mock(Establecimiento.class);
        when(establecimientoRepository.findById(1L)).thenReturn(Optional.of(establecimiento));
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);
        Habitacion resultado = habitacionService.crearHabitacion(habitacion, 1L);
        assertNotNull(resultado);
        verify(habitacionRepository, times(1)).save(habitacion);
    }

    @Test
    void crearHabitacion_lanzaExcepcion_cuandoEstablecimientoNoExiste() {
        when(establecimientoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> habitacionService.crearHabitacion(habitacion, 1L));
    }

    @Test
    void estaDisponible_devuelveTrue_cuandoNoHayReservas() {
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        boolean resultado = habitacionService.estaDisponible(1L,
                LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 15));
        assertTrue(resultado);
    }

    @Test
    void estaDisponible_devuelveFalse_cuandoHayReservaSolapada() {
        Reserva reserva = new Reserva(
                LocalDate.of(2025, 6, 8),
                LocalDate.of(2025, 6, 12),
                Estado.PENDIENTE, 0.0);
        habitacion.setReservas(List.of(reserva));
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        boolean resultado = habitacionService.estaDisponible(1L,
                LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 15));
        assertFalse(resultado);
    }

    @Test
    void estaDisponible_devuelveTrue_cuandoReservaEstaCancelada() {
        Reserva reserva = new Reserva(
                LocalDate.of(2025, 6, 8),
                LocalDate.of(2025, 6, 12),
                Estado.CANCELADA, 0.0);
        habitacion.setReservas(List.of(reserva));
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        boolean resultado = habitacionService.estaDisponible(1L,
                LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 15));
        assertTrue(resultado);
    }

    @Test
    void eliminarHabitacion_eliminaCorrectamente_cuandoExiste() {
        when(habitacionRepository.existsById(1L)).thenReturn(true);
        habitacionService.eliminarHabitacion(1L);
        verify(habitacionRepository, times(1)).deleteById(1L);
    }
}