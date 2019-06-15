package com.juanmagarcia.pillalas.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Observatorio.
 */
@Entity
@Table(name = "observatorio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Observatorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private String latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private String longitud;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "observatorio_observatorio",
               joinColumns = @JoinColumn(name = "observatorio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "observatorio_id", referencedColumnName = "id"))
    private Set<Fotografia> observatorios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "observatorio_ave",
               joinColumns = @JoinColumn(name = "observatorio_id", referencedColumnName = "id"),
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

    public Observatorio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public Observatorio latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public Observatorio longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Observatorio foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Observatorio fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Observatorio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Fotografia> getObservatorios() {
        return observatorios;
    }

    public Observatorio observatorios(Set<Fotografia> fotografias) {
        this.observatorios = fotografias;
        return this;
    }

    public Observatorio addObservatorio(Fotografia fotografia) {
        this.observatorios.add(fotografia);
        fotografia.getObservatorios().add(this);
        return this;
    }

    public Observatorio removeObservatorio(Fotografia fotografia) {
        this.observatorios.remove(fotografia);
        fotografia.getObservatorios().remove(this);
        return this;
    }

    public void setObservatorios(Set<Fotografia> fotografias) {
        this.observatorios = fotografias;
    }

    public Set<Ave> getAves() {
        return aves;
    }

    public Observatorio aves(Set<Ave> aves) {
        this.aves = aves;
        return this;
    }

    public Observatorio addAve(Ave ave) {
        this.aves.add(ave);
        ave.getObservatorios().add(this);
        return this;
    }

    public Observatorio removeAve(Ave ave) {
        this.aves.remove(ave);
        ave.getObservatorios().remove(this);
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
        if (!(o instanceof Observatorio)) {
            return false;
        }
        return id != null && id.equals(((Observatorio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Observatorio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
