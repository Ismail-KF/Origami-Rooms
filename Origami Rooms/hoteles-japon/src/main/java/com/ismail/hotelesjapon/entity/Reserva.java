package com.ismail.hotelesjapon.entity;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Representa una Reserva
 * <p>
 * Estas serán las distintas reservas llevadas a cabo por diferentes
 * clientes y
 * que irán asociadas a una o más habitaciones de un establecimiento.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Cliente
 * @see Habitacion
 * @see Establecimiento
 */
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado_reserva", nullable = false)
    @Enumerated(EnumType.STRING)
    private Reserva.Estado estadoReserva;

    public enum Estado {
        COMPLETADA,
        CANCELADA,
        PENDIENTE
    }

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @Column(name = "numero_habitaciones", nullable = false)
    private Integer numeroHabitaciones = 0;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reserva_habitacion", joinColumns = @JoinColumn(name = "reserva_id"), inverseJoinColumns = @JoinColumn(name = "habitacion_id"))
    @JsonIgnoreProperties("reservas")
    private List<Habitacion> habitaciones;

    public Reserva() {
    }

    public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, Estado estadoReserva, Double precioTotal) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estadoReserva = estadoReserva;
        this.precioTotal = precioTotal;
    }

    /**
     * @return ID único de la reserva
     * @author Ismail
     * @since 1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Fecha en la que llegarán los clientes
     * @author Ismail
     * @since 1.0
     */
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    /**
     * @param fechaEntrada Fecha en la que llegarán los clientes
     * @author Ismail
     * @since 1.0
     */
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    /**
     * @return Fecha en la que se irán los clientes
     * @author Ismail
     * @since 1.0
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida Fecha en la que se irán los clientes
     * @author Ismail
     * @since 1.0
     */
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * @return Estado en el que se encuentra la reserva. "COMPLETADA" si la misma ya
     *         ha sido pagada, "CANCELADA" si el cliente ha cancelado la reserva o
     *         "PENDIENTE" si se ha realizado una reserva pero aún no ha sido
     *         pagada.
     * 
     * @author Ismail
     * @since 1.0
     */
    public Reserva.Estado getEstadoReserva() {
        return estadoReserva;
    }

    /**
     * @param estadoReserva Estado en el que se encuentra la reserva. "COMPLETADA"
     *                      si la misma ya ha sido pagada, "CANCELADA" si el cliente
     *                      ha cancelado la reserva o "PENDIENTE" si se ha realizado
     *                      una reserva pero aún no ha sido pagada.
     * 
     * @author Ismail
     * @since 1.0
     */
    public void setEstadoReserva(Reserva.Estado estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    /**
     * @return Precio total de la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public Double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * @param precioTotal Precio total de la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    /**
     * @return Cliente que ha realizado la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente Cliente que ha realizado la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return Lista de las habitaciones incluidas en la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * @param habitaciones Lista de habitaciones incluidas en la reserva
     */
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
        this.numeroHabitaciones = habitaciones != null ? habitaciones.size() : 0;
    }

    /**
     * @return Número de habitaciones que incluye la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public Integer getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    /**
     * @param numeroHabitaciones Número de habitaciones que incluye la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    public void setNumeroHabitaciones(Integer numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    /**
     * @return Todos los datos de la reserva
     * 
     * @author Ismail
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Reserva [id=" + id + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida
                + ", estadoReserva=" + estadoReserva + ", precioTotal=" + precioTotal
                + ", clienteId=" + (cliente != null ? cliente.getId() : null)
                + ", habitaciones=" + habitaciones + "]";
    }
}