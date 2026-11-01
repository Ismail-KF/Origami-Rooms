package com.ismail.hotelesjapon.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;

/**
 * Representa un Establecimiento
 * 
 * <p>
 * Un establecimiento con una serie de habitaciones y que servirá de molde para
 * los dos tipos de establecimientos en los que se podrá reservar una
 * habitación. Por un lado hoteles «ordinarios» y por el otro ryokan,
 * alojamientos más tradicionales japoneses.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Habitacion
 * @see Hotel
 * @see Ryokan
 */
@Entity
@Table(name = "establecimientos")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Hotel.class, name = "hotel"),
        @JsonSubTypes.Type(value = Ryokan.class, name = "ryokan")
})
public abstract class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "nombre", nullable = false)
    protected String nombre;

    @Column(name = "descripción", nullable = false)
    protected String descripcion;

    @Column(name = "direccion", nullable = false)
    protected String direccion;

    @Column(name = "region", nullable = false)
    @Enumerated(EnumType.STRING)
    protected Establecimiento.Region region;

    public enum Region {
        HOKKAIDOU,
        TOHOKU,
        KANTOU,
        CHUUGOKU,
        SHIKOKU,
        KANSAI,
        KYUUSHUU,
        CHUUBU
    }

    @ElementCollection
    protected List<Integer> puntuaciones;

    @Column(name = "puntuacion_media")
    protected Double puntuacionMedia;

    @JsonIgnoreProperties("establecimiento")
    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected List<Habitacion> habitaciones;

    public Establecimiento() {
    }

    public Establecimiento(String nombre, String descripcion, String direccion, Region region,
            List<Integer> puntuaciones, Double puntuacionMedia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.region = region;
        this.puntuaciones = puntuaciones;
        this.puntuacionMedia = puntuacionMedia;
    }

    /**
     * @return ID único del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Nombre del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre Nombre del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return Descripción acerca del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion Descripción acerca del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return Dirección en la que se encuentra el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion Dirección en la que se encuentra el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return Región de Japón donde se encuentra el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public Establecimiento.Region getRegion() {
        return region;
    }

    /**
     * @param region Región de Japón donde se encuentra el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setRegion(Establecimiento.Region region) {
        this.region = region;
    }

    /**
     * @return Lista de las puntuaciones dejadas por los diferentes {@link Cliente}
     * 
     * @author Ismail
     * @version 1.0
     */
    public List<Integer> getPuntuaciones() {
        return puntuaciones;
    }

    /**
     * @param puntuaciones Lista de puntuaciones dejadas por los diferentes
     *                     {@link Cliente}
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setPuntuaciones(List<Integer> puntuaciones) {
        this.puntuaciones = puntuaciones;
        calcularMedia();
    }

    /**
     * @return Puntuación media de todas las puntuaciones dejadas por los
     *         {@link Cliente}
     * 
     * @author Ismail
     * @version 1.0
     */
    public Double getPuntuacionMedia() {
        return puntuacionMedia;
    }

    /**
     * @param puntuacionMedia Puntuación media de todas las puntuaciones dejadas por
     *                        los {@link Cliente}
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setPuntuacionMedia(Double puntuacionMedia) {
        this.puntuacionMedia = puntuacionMedia;
    }

    /**
     * 
     * @return Habitaciones que tiene el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * 
     * @param habitaciones Habitaciones que tiene el establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Método que sirve para agregar una puntuación al establecimiento. Si la
     * puntuación no está comprendida entre 1 y 10 da error.
     * 
     * @param puntuacion Puntuación dejada por el cliente
     * 
     * @author Ismail
     * @version 1.0
     */
    public void agregarPuntuacion(Integer puntuacion) {
        if (this.puntuaciones == null) {
            this.puntuaciones = new ArrayList<>();
        }

        if (puntuacion < 1 || puntuacion > 10) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 10");
        } else {
            this.puntuaciones.add(puntuacion);
            calcularMedia();
        }
    }

    /**
     * Método para calcular le puntuación media del establecimiento. Recorre todas
     * las puntuaciones, las va sumando y finalmente divide el número resultante
     * entre el número de puntuaciones.
     * 
     * @author Ismail
     * @version 1.0
     */
    public void calcularMedia() {
        if (puntuaciones == null || puntuaciones.isEmpty()) {
            this.puntuacionMedia = 0.0;
        } else {
            Double suma = 0.0;
            for (Integer p : puntuaciones) {
                suma += p;
            }
            this.puntuacionMedia = suma / puntuaciones.size();
        }
    }

    /**
     * @return Toda la información acerca del establecimiento
     * 
     * @author Ismail
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Establecimiento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", direccion="
                + direccion + ", region=" + region + ", puntuacionMedia=" + puntuacionMedia + ", habitaciones="
                + habitaciones + "]";
    }
}