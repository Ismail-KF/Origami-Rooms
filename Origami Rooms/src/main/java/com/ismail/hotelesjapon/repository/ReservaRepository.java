package com.ismail.hotelesjapon.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ismail.hotelesjapon.entity.Reserva;
import com.ismail.hotelesjapon.entity.Reserva.Estado;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r WHERE " +
            "(:clienteId IS NULL OR r.cliente.id = :clienteId) AND " +
            "(:estado IS NULL OR r.estadoReserva = :estado) AND " +
            "(:fechaInicio IS NULL OR r.fechaEntrada = :fechaInicio) AND " +
            "(:fechaFin IS NULL OR r.fechaSalida = :fechaFin)")
    List<Reserva> buscarReservasPorFiltros(@Param("clienteId") Long clienteId,
            @Param("estado") Estado estado,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}