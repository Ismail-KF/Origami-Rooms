package com.ismail.hotelesjapon.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Representa una Habitación
 * <p>
 * Una habitación de un establecimiento y que podrá tener (o no) una
 * reserva (o varias) asociadas realizadas por un cliente.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Cliente
 * @see Reserva
 * @see Establecimiento
 */
@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero_habitacion", nullable = false)
    private String numeroHabitacion;

    @Column(name = "tipo_habitacion", nullable = false)
    @Enumerated(EnumType.STRING)
    private Habitacion.Tipo tipoHabitacion;

    public enum Tipo {
        INDIVIDUAL,
        DOBLE,
        TRIPLE,
        ECONOMICA,
        SUITE
    }

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "precio_por_noche", nullable = false)
    private Double precioPorNoche;

    @JsonIgnoreProperties("habitaciones")
    @ManyToMany(mappedBy = "habitaciones")
    private List<Reserva> reservas;

    @JsonIgnoreProperties("habitaciones")
    @ManyToOne
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;

    public Habitacion() {
    }

    public Habitacion(String numeroHabitacion, Tipo tipoHabitacion, Integer capacidad, Double precioPorNoche) {
        this.numeroHabitacion = numeroHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.capacidad = capacidad;
        this.precioPorNoche = precioPorNoche;
    }

    /**
     * @return ID único de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * @return El número de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    /**
     * @param numeroHabitación El número de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setNumeroHabitacion(String numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    /**
     * @return EL tipo de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public Habitacion.Tipo getTipoHabitacion() {
        return tipoHabitacion;
    }

    /**
     * @param tipoHabitación El tipo de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTipoHabitacion(Habitacion.Tipo tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    /**
     * @return La capacidad máxima de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad La capacidad máxima de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return El precio de la habitación por noche
     * 
     * @author Ismail
     * @version 1.0
     */
    public Double getPrecioPorNoche() {
        return precioPorNoche;
    }

    /**
     * @param precioPorNoche El precio de la habitación por noche
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setPrecioPorNoche(Double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    /**
     * @return Reservas que tiene la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public List<Reserva> getReservas() {
        return reservas;
    }

    /**
     * @param reservas Las reservas que tiene la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * @return Establecimiento al cual pertenece la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento Establecimiento al cual pertenece la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return Toda la información de la habitación
     * 
     * @author Ismail
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Habitacion [id=" + id + ", numeroHabitacion=" + numeroHabitacion + ", tipoHabitacion=" + tipoHabitacion
                + ", capacidad=" + capacidad + ", precioPorNoche=" + precioPorNoche + ", reservas="
                + (reservas != null ? reservas : "Sin reservas actuales.")
                + ", establecimiento=" + establecimiento + "]";
    }
}