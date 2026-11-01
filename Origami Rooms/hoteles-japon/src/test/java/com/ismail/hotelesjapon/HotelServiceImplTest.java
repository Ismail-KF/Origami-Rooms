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
import com.ismail.hotelesjapon.entity.Hotel;
import com.ismail.hotelesjapon.repository.HotelRepository;
import com.ismail.hotelesjapon.service.impl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel("Hotel Tokio", "Hotel en Tokio", "1-1 Shinjuku, Tokio",
                Region.KANTOU, new ArrayList<>(), 0.0, 4, true, false, true);
    }

    @Test
    void buscarPorId_devuelveHotel_cuandoExiste() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        Hotel resultado = hotelService.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Hotel Tokio", resultado.getNombre());
    }

    @Test
    void buscarPorId_lanzaExcepcion_cuandoNoExiste() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> hotelService.buscarPorId(1L));
    }

    @Test
    void crearHotel_guardaYDevuelve_cuandoDatosCorrectos() {
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        Hotel resultado = hotelService.crearHotel(hotel);
        assertNotNull(resultado);
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void crearHotel_lanzaExcepcion_cuandoEstrellasInvalidas() {
        hotel.setEstrellas(6);
        assertThrows(RuntimeException.class, () -> hotelService.crearHotel(hotel));
    }

    @Test
    void crearHotel_lanzaExcepcion_cuandoHotel5EstrellasSinServicios() {
        hotel.setEstrellas(5);
        hotel.setTieneGimnasio(false);
        assertThrows(IllegalArgumentException.class, () -> hotelService.crearHotel(hotel));
    }

    @Test
    void crearHotel_lanzaExcepcion_cuandoHotel4EstrellasSinRestaurante() {
        hotel.setEstrellas(4);
        hotel.setTieneRestaurante(false);
        assertThrows(IllegalArgumentException.class, () -> hotelService.crearHotel(hotel));
    }

    @Test
    void crearHotel_lanzaExcepcion_cuandoHotel3EstrellasSinRestaurante() {
        hotel.setEstrellas(3);
        hotel.setTieneRestaurante(false);
        assertThrows(IllegalArgumentException.class, () -> hotelService.crearHotel(hotel));
    }

    @Test
    void eliminarHotel_eliminaCorrectamente_cuandoExiste() {
        when(hotelRepository.existsById(1L)).thenReturn(true);
        hotelService.eliminarHotel(1L);
        verify(hotelRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarHotel_lanzaExcepcion_cuandoNoExiste() {
        when(hotelRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> hotelService.eliminarHotel(1L));
    }
}