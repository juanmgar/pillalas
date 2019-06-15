package com.juanmagarcia.pillalas.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Avistamiento.
 */
@Entity
@Table(name = "avistamiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Avistamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private String latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private String longitud;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "avistamiento_foto",
               joinColumns = @JoinColumn(name = "avistamiento_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "foto_id", referencedColumnName = "id"))
    private Set<Fotografia> fotos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "avistamiento_ave",
               joinColumns = @JoinColumn(name = "avistamiento_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ave_id", referencedColumnName = "id"))
    private Set<Ave> aves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Avistamiento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Avistamiento fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getLatitud() {
        return latitud;
    }

    public Avistamiento latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public Avistamiento longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Avistamiento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Fotografia> getFotos() {
        return fotos;
    }

    public Avistamiento fotos(Set<Fotografia> fotografias) {
        this.fotos = fotografias;
        return this;
    }

    public Avistamiento addFoto(Fotografia fotografia) {
        this.fotos.add(fotografia);
        fotografia.getAvistamientos().add(this);
        return this;
    }

    public Avistamiento removeFoto(Fotografia fotografia) {
        this.fotos.remove(fotografia);
        fotografia.getAvistamientos().remove(this);
        return this;
    }

    public void setFotos(Set<Fotografia> fotografias) {
        this.fotos = fotografias;
    }

    public Set<Ave> getAves() {
        return aves;
    }

    public Avistamiento aves(Set<Ave> aves) {
        this.aves = aves;
        return this;
    }

    public Avistamiento addAve(Ave ave) {
        this.aves.add(ave);
        ave.getAvistamientos().add(this);
        return this;
    }

    public Avistamiento removeAve(Ave ave) {
        this.aves.remove(ave);
        ave.getAvistamientos().remove(this);
        return this;
    }

    public void setAves(Set<Ave> aves) {
        this.aves = aves;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avistamiento)) {
            return false;
        }
        return id != null && id.equals(((Avistamiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Avistamiento{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
