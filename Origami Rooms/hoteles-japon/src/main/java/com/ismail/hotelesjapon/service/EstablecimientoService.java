package com.ismail.hotelesjapon.service;

import java.util.List;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;

public interface EstablecimientoService {
    List<Establecimiento> mostrarTodos();

    List<Establecimiento> buscarEstablecimientos(String nombre, String direccion, Region region,
            Double puntuacionMediaMinima);

    Establecimiento buscarPorId(Long id);

    Establecimiento crearEstablecimiento(Establecimiento establecimiento);

    Establecimiento actualizarEstablecimiento(Long id, Establecimiento establecimiento);

    Establecimiento actualizarEstablecimientoParcialmente(Long id, Establecimiento establecimiento);

    void eliminarEstablecimiento(Long id);
}