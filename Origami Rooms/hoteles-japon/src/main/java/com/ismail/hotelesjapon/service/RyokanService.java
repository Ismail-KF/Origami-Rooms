package com.ismail.hotelesjapon.service;

import java.util.List;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Ryokan;

public interface RyokanService {
    List<Ryokan> mostrarTodos();

    List<Ryokan> buscarRyokan(Region region, Double puntuacionMediaMinima, Integer estrellas, Boolean tieneOnsen,
            Boolean tieneKimono, Boolean tieneJardin);

    Ryokan buscarPorId(Long id);

    Ryokan crearRyokan(Ryokan ryokan);

    Ryokan actualizarRyokan(Long id, Ryokan ryokan);

    Ryokan actualizarRyokanParcialmente(Long id, Ryokan ryokan);

    void eliminarRyokan(Long id);
}