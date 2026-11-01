package com.ismail.hotelesjapon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Hotel;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE " +
            "(:region IS NULL OR h.region = :region) AND " +
            "(:puntuacionMediaMin IS NULL OR h.puntuacionMedia >= :puntuacionMediaMin) AND " +
            "(:estrellas IS NULL OR h.estrellas = :estrellas) AND " +
            "(:tieneRestaurante IS NULL OR h.tieneRestaurante = :tieneRestaurante) AND " +
            "(:tieneGimnasio IS NULL OR h.tieneGimnasio = :tieneGimnasio) AND " +
            "(:tieneServicioHabitaciones IS NULL OR h.tieneServicioHabitaciones = :tieneServicioHabitaciones)")
    List<Hotel> buscarHotelesPorFiltros(@Param("region") Region region,
            @Param("puntuacionMediaMin") Double puntuacionMediaMin,
            @Param("estrellas") Integer estrellas,
            @Param("tieneRestaurante") Boolean tieneRestaurante,
            @Param("tieneGimnasio") Boolean tieneGimnasio,
            @Param("tieneServicioHabitaciones") Boolean tieneServicioHabitaciones);
}