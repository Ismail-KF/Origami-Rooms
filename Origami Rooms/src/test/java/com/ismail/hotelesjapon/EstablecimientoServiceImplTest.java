package com.ismail.hotelesjapon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Hotel;
import com.ismail.hotelesjapon.repository.EstablecimientoRepository;
import com.ismail.hotelesjapon.service.impl.EstablecimientoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EstablecimientoServiceImplTest {

    @Mock
    private EstablecimientoRepository establecimientoRepository;

    @InjectMocks
    private EstablecimientoServiceImpl establecimientoService;

    private Establecimiento establecimiento;

    @BeforeEach
    void setUp() {
        establecimiento = new Hotel("Hotel Tokio", "Hotel en Tokio", "1-1 Shinjuku, Tokio",
                Region.KANTOU, new ArrayList<>(), 0.0, 4, true, false, true);
    }

    @Test
    void mostrarTodos_devuelveLista_cuandoHayEstablecimientos() {
        when(establecimientoRepository.findAll()).thenReturn(List.of(establecimiento));
        List<Establecimiento> resultado = establecimientoService.mostrarTodos();
        assertFalse(resultado.isEmpty());
    }

    @Test
    void mostrarTodos_lanzaExcepcion_cuandoNoHayEstablecimientos() {
        when(establecimientoRepository.findAll()).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> establecimientoService.mostrarTodos());
    }

    @Test
    void buscarPorId_devuelveEstablecimiento_cuandoExiste() {
        when(establecimientoRepository.findById(1L)).thenReturn(Optional.of(establecimiento));
        Establecimiento resultado = establecimientoService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Hotel Tokio", resultado.getNombre());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(establecimientoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> establecimientoService.buscarPorId(1L));
    }

    @Test
    void crearEstablecimiento_guardaYDevuelve_cuandoDatosCorrectos() {
        when(establecimientoRepository.save(establecimiento)).thenReturn(establecimiento);
        Establecimiento resultado = establecimientoService.crearEstablecimiento(establecimiento);
        assertNotNull(resultado);
        verify(establecimientoRepository, times(1)).save(establecimiento);
    }

    @Test
    void crearEstablecimiento_lanzaExcepcion_cuandoNombreNulo() {
        establecimiento.setNombre(null);
        assertThrows(RuntimeException.class, () -> establecimientoService.crearEstablecimiento(establecimiento));
    }

    @Test
    void crearEstablecimiento_lanzaExcepcion_cuandoRegionNula() {
        establecimiento.setRegion(null);
        assertThrows(RuntimeException.class, () -> establecimientoService.crearEstablecimiento(establecimiento));
    }

    @Test
    void eliminarEstablecimiento_eliminaCorrectamente_cuandoExiste() {
        when(establecimientoRepository.existsById(1L)).thenReturn(true);
        establecimientoService.eliminarEstablecimiento(1L);
        verify(establecimientoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarEstablecimiento_lanzaExcepcion_cuandoNoExiste() {
        when(establecimientoRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> establecimientoService.eliminarEstablecimiento(1L));
    }
}