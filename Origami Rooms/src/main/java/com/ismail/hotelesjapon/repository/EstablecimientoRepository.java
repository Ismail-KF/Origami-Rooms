package com.ismail.hotelesjapon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Establecimiento;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;

@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {
    @Query("SELECT e FROM Establecimiento e WHERE " +
            "(:nombre IS NULL OR e.nombre LIKE CONCAT('%', :nombre, '%')) AND " +
            "(:direccion IS NULL OR e.direccion LIKE CONCAT('%', :direccion, '%')) AND " +
            "(:region IS NULL OR e.region = :region) AND " +
            "(:puntuacionMediaMin IS NULL OR e.puntuacionMedia >= :puntuacionMediaMin)")
    List<Establecimiento> buscarEstablecimientosPorFiltros(
            @Param("nombre") String nombre,
            @Param("direccion") String direccion,
            @Param("region") Region region,
            @Param("puntuacionMediaMin") Double puntuacionMediaMin);
}