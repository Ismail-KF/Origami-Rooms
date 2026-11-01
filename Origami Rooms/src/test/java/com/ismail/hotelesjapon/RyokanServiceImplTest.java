package com.ismail.hotelesjapon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Ryokan;
import com.ismail.hotelesjapon.repository.RyokanRepository;
import com.ismail.hotelesjapon.service.impl.RyokanServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RyokanServiceImplTest {

    @Mock
    private RyokanRepository ryokanRepository;

    @InjectMocks
    private RyokanServiceImpl ryokanService;

    private Ryokan ryokan;

    @BeforeEach
    void setUp() {
        ryokan = new Ryokan("Ryokan Arashiyama", "Ryokan tradicional en Kioto",
                "11 Saga Oguracho, Kioto", Region.KANSAI,
                new ArrayList<>(), 0.0, 5, true, true, true);
    }

    @Test
    void buscarPorId_devuelveRyokan_cuandoExiste() {
        when(ryokanRepository.findById(1L)).thenReturn(Optional.of(ryokan));
        Ryokan resultado = ryokanService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Ryokan Arashiyama", resultado.getNombre());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(ryokanRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> ryokanService.buscarPorId(1L));
    }

    @Test
    void crearRyokan_guardaYDevuelve_cuandoDatosCorrectos() {
        when(ryokanRepository.save(ryokan)).thenReturn(ryokan);
        Ryokan resultado = ryokanService.crearRyokan(ryokan);
        assertNotNull(resultado);
        verify(ryokanRepository, times(1)).save(ryokan);
    }

    @Test
    void crearRyokan_lanzaExcepcion_cuandoEstrellasInvalidas() {
        ryokan.setEstrellas(6);
        assertThrows(RuntimeException.class, () -> ryokanService.crearRyokan(ryokan));
    }

    @Test
    void crearRyokan_lanzaExcepcion_cuandoRyokan5EstrellasSinOnsen() {
        ryokan.setEstrellas(5);
        ryokan.setTieneOnsen(false);
        assertThrows(IllegalArgumentException.class, () -> ryokanService.crearRyokan(ryokan));
    }

    @Test
    void crearRyokan_lanzaExcepcion_cuandoRyokan4EstrellasSinJardin() {
        ryokan.setEstrellas(4);
        ryokan.setTieneJardin(false);
        assertThrows(IllegalArgumentException.class, () -> ryokanService.crearRyokan(ryokan));
    }

    @Test
    void crearRyokan_lanzaExcepcion_cuandoRyokan3EstrellasSinKimono() {
        ryokan.setEstrellas(3);
        ryokan.setTieneKimono(false);
        assertThrows(IllegalArgumentException.class, () -> ryokanService.crearRyokan(ryokan));
    }

    @Test
    void eliminarRyokan_eliminaCorrectamente_cuandoExiste() {
        when(ryokanRepository.existsById(1L)).thenReturn(true);
        ryokanService.eliminarRyokan(1L);
        verify(ryokanRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarRyokan_lanzaExcepcion_cuandoNoExiste() {
        when(ryokanRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> ryokanService.eliminarRyokan(1L));
    }
}