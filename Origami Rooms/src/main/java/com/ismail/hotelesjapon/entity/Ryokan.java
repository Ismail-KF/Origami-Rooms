package com.ismail.hotelesjapon.entity;

import java.util.List;
import jakarta.persistence.*;

/**
 * Representa un Ryokan
 * 
 * <p>
 * Este será uno de los dos tipos de establecimiento que habrá junto con los
 * hoteles.
 * </p>
 * 
 * @author Ismail
 * @version 1.0
 * @see Establecimiento
 * @see Hotel
 */
@Entity
@Table(name = "ryokan")
public class Ryokan extends Establecimiento {
    @Column(name = "estrellas", nullable = false)
    private Integer estrellas;

    @Column(name = "onsen", nullable = false)
    private Boolean tieneOnsen;

    @Column(name = "servicio_kimono", nullable = false)
    private Boolean tieneKimono;

    @Column(name = "jardin", nullable = false)
    private Boolean tieneJardin;

    public Ryokan() {
    }

    public Ryokan(String nombre, String descripcion, String direccion, Region region, List<Integer> puntuaciones,
            Double puntuacionMedia, Integer estrellas, Boolean tieneOnsen, Boolean tieneKimono, Boolean tieneJardin) {
        super(nombre, descripcion, direccion, region, puntuaciones, puntuacionMedia);
        this.estrellas = estrellas;
        this.tieneOnsen = tieneOnsen;
        this.tieneKimono = tieneKimono;
        this.tieneJardin = tieneJardin;
    }

    /**
     * @return Número de estrellas del ryokan
     * 
     * @author Ismail
     * @version 1.0
     */
    public Integer getEstrellas() {
        return estrellas;
    }

    /**
     * @param estrellas Número de estrellas del ryokan
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * @return Si el ryokan tiene onsen o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public Boolean getTieneOnsen() {
        return tieneOnsen;
    }

    /**
     * @param tieneOnsen Si el ryokan tiene onsen o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTieneOnsen(Boolean tieneOnsen) {
        this.tieneOnsen = tieneOnsen;
    }

    /**
     * @return Si el ryokan tiene servicio de vestimenta con kimono o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public Boolean getTieneKimono() {
        return tieneKimono;
    }

    /**
     * @param tieneKimono Si el ryokan tiene servicio de vestimenta con kimono o no
     * 
     * @author Ismail
     * @version 1.0
     */
    public void setTieneKimono(Boolean tieneKimono) {
        this.tieneKimono = tieneKimono;
    }

    /**
     * @return Si el ryokan tiene jardín tradicional o no
     */
    public Boolean getTieneJardin() {
        return tieneJardin;
    }

    /**
     * @param tieneJardin Si el ryokan tiene jardín tradicional o no
     */
    public void setTieneJardin(Boolean tieneJardin) {
        this.tieneJardin = tieneJardin;
    }

    /**
     * @return Todos los datos del ryokan
     */
    @Override
    public String toString() {
        return "Ryokan [ " + super.toString() + ", estrellas=" + estrellas + ", tieneOnsen=" + tieneOnsen
                + ", tieneKimono=" + tieneKimono
                + ", tieneJardin=" + tieneJardin + "]";
    }
}