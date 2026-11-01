package com.ismail.hotelesjapon.entity;

import java.util.List;
import jakarta.persistence.*;

/**
 * Representa un Hotel
 * 
 * <p>
 * Este será uno de los dos tipos de establecimiento que habrá junto con los
 * ryokan.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Establecimiento
 * @see Ryokan
 */
@Entity
@Table(name = "hoteles")
public class Hotel extends Establecimiento {
    @Column(name = "estrellas", nullable = false)
    private Integer estrellas;

    @Column(name = "restaurante", nullable = false)
    private Boolean tieneRestaurante;

    @Column(name = "gimnasio", nullable = false)
    private Boolean tieneGimnasio;

    @Column(name = "servicio_de_habitaciones", nullable = false)
    private Boolean tieneServicioHabitaciones;

    public Hotel() {
    }

    public Hotel(String nombre, String descripcion, String direccion, Region region, List<Integer> puntuaciones,
            Double puntuacionMedia, Integer estrellas, Boolean tieneRestaurante, Boolean tieneGimnasio,
            Boolean tieneServicioHabitaciones) {
        super(nombre, descripcion, direccion, region, puntuaciones, puntuacionMedia);
        this.estrellas = estrellas;
        this.tieneRestaurante = tieneRestaurante;
        this.tieneGimnasio = tieneGimnasio;
        this.tieneServicioHabitaciones = tieneServicioHabitaciones;
    }

    /**
     * @return Número de estrellas del hotel
     * 
     * @author Ismail
     * @version 1.0
     */
    public Integer getEstrellas() {
        return estrellas;
    }

    /**
     * @param estrellas Número de estrellas del hotel
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * @return Si tiene restaurante o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public Boolean getTieneRestaurante() {
        return tieneRestaurante;
    }

    /**
     * @param tieneRestaurante Si el hotel tiene restaurante o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTieneRestaurante(Boolean tieneRestaurante) {
        this.tieneRestaurante = tieneRestaurante;
    }

    /**
     * @return Si tiene gimnasio o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public Boolean getTieneGimnasio() {
        return tieneGimnasio;
    }

    /**
     * @param tieneGimnasio Si el hotel tiene gimnasio o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTieneGimnasio(Boolean tieneGimnasio) {
        this.tieneGimnasio = tieneGimnasio;
    }

    /**
     * @return Si tiene servicio de habitaciones o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public Boolean getTieneServicioHabitaciones() {
        return tieneServicioHabitaciones;
    }

    /**
     * @param tieneServicioHabitaciones Si el hotel tiene servicio de habitaciones o
     *                                  no
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTieneServicioHabitaciones(Boolean tieneServicioHabitaciones) {
        this.tieneServicioHabitaciones = tieneServicioHabitaciones;
    }

    /**
     * @return Todos los datos del hotel
     * 
     * @author Ismail
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Hotel [ " + super.toString() + ", estrellas=" + estrellas + ", tieneRestaurante=" + tieneRestaurante
                + ", tieneGimnasio="
                + tieneGimnasio + ", tieneServicioHabitaciones=" + tieneServicioHabitaciones + "]";
    }
}