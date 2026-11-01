package com.ismail.hotelesjapon.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Representa a un Cliente
 * <p>
 * Estos serán los que lleven a cabo las reservas de las diferentes habitaciones
 * en los distintos establecientos.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Reserva
 * @see Habitacion
 * @see Establecimiento
 */
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String email;

    @Column(name = "numero_telefono", nullable = false, unique = true)
    private String telefono;

    @Column(name = "pasaporte", nullable = false, unique = true)
    private String pasaporte;

    @Column(name = "nacionalidad", nullable = false)
    private String nacionalidad;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Reserva> reservasCliente = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String email, String telefono, String pasaporte) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.pasaporte = pasaporte;
    }

    /**
     * @return ID único del cliente
     * @author Ismail
     * @since 1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * @return nombre del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nombre del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return apellido del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido apellido del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return correo electrónico del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email correo electrónico del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return número de teléfono del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono número de teléfono del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return pasaporte del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getPasaporte() {
        return pasaporte;
    }

    /**
     * @param pasaporte pasaporte del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    /**
     * @return lista de todas las reservas que tiene el cliente
     * @author Ismail
     * @since 1.0
     */
    public List<Reserva> getReservasCliente() {
        return reservasCliente;
    }

    /**
     * @param reservasCliente lista de reservas que tiene el cliente
     * @author Ismail
     * @since 1.0
     */
    public void setReservasCliente(List<Reserva> reservasCliente) {
        this.reservasCliente = reservasCliente;
    }

    /**
     * @return nacionalidad del cliente
     * @author Ismail
     * @since 1.0
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad nacionalidad del cliente
     * @author Ismail
     * @since 1.0
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
                + ", telefono=" + telefono + ", pasaporte=" + pasaporte + ", nacionalidad=" + nacionalidad
                + ", reservasCliente="
                + (reservasCliente != null ? reservasCliente : "Este cliente no tiene reservas todavía") + "]";
    }
}