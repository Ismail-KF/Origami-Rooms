package com.ismail.hotelesjapon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Habitacion;
import com.ismail.hotelesjapon.entity.Habitacion.Tipo;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    @Query("SELECT h FROM Habitacion h WHERE " +
            "(:tipo IS NULL OR h.tipoHabitacion = :tipo) AND " +
            "(:capacidadMin IS NULL OR h.capacidad >= :capacidadMin) AND " +
            "(:precioMax IS NULL OR h.precioPorNoche <= :precioMax)")
    List<Habitacion> buscarHabitacionesPorFiltros(@Param("tipo") Tipo tipo,
            @Param("capacidadMin") Integer capacidadMin,
            @Param("precioMax") Double precioMax);
}