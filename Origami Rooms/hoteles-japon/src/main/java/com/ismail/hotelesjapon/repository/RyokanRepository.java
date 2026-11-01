package com.ismail.hotelesjapon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Establecimiento.Region;
import com.ismail.hotelesjapon.entity.Ryokan;
import java.util.List;

@Repository
public interface RyokanRepository extends JpaRepository<Ryokan, Long> {
    @Query("SELECT r FROM Ryokan r WHERE " +
            "(:region IS NULL OR r.region = :region) AND " +
            "(:puntuacionMediaMin IS NULL OR r.puntuacionMedia >= :puntuacionMediaMin) AND " +
            "(:estrellas IS NULL OR r.estrellas = :estrellas) AND " +
            "(:tieneOnsen IS NULL OR r.tieneOnsen = :tieneOnsen) AND " +
            "(:tieneKimono IS NULL OR r.tieneKimono = :tieneKimono) AND " +
            "(:tieneJardin IS NULL OR r.tieneJardin = :tieneJardin)")
    List<Ryokan> buscarRyokanPorFiltros(@Param("region") Region region,
            @Param("puntuacionMediaMin") Double puntuacionMediaMin,
            @Param("estrellas") Integer estrellas,
            @Param("tieneOnsen") Boolean tieneOnsen,
            @Param("tieneKimono") Boolean tieneKimono,
            @Param("tieneJardin") Boolean tieneJardin);
}