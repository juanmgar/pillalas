package com.juanmagarcia.pillalas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ave.
 */
@Entity
@Table(name = "ave")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_comun", nullable = false)
    private String nombreComun;

    @NotNull
    @Column(name = "nombre_cientifico", nullable = false)
    private String nombreCientifico;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Lob
    @Column(name = "sonido")
    private byte[] sonido;

    @Column(name = "sonido_content_type")
    private String sonidoContentType;

    @ManyToOne
    @JsonIgnoreProperties("aves")
    private User autor;

    @ManyToMany(mappedBy = "aves")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Fotografia> fotos = new HashSet<>();

    @ManyToMany(mappedBy = "aves")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Observatorio> observatorios = new HashSet<>();

    @ManyToMany(mappedBy = "aves")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Avistamiento> avistamientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public Ave nombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
        return this;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public Ave nombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
        return this;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Ave descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Ave foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Ave fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public byte[] getSonido() {
        return sonido;
    }

    public Ave sonido(byte[] sonido) {
        this.sonido = sonido;
        return this;
    }

    public void setSonido(byte[] sonido) {
        this.sonido = sonido;
    }

    public String getSonidoContentType() {
        return sonidoContentType;
    }

    public Ave sonidoContentType(String sonidoContentType) {
        this.sonidoContentType = sonidoContentType;
        return this;
    }

    public void setSonidoContentType(String sonidoContentType) {
        this.sonidoContentType = sonidoContentType;
    }

    public User getAutor() {
        return autor;
    }

    public Ave autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public Set<Fotografia> getFotos() {
        return fotos;
    }

    public Ave fotos(Set<Fotografia> fotografias) {
        this.fotos = fotografias;
        return this;
    }

    public Ave addFoto(Fotografia fotografia) {
        this.fotos.add(fotografia);
        fotografia.getAves().add(this);
        return this;
    }

    public Ave removeFoto(Fotografia fotografia) {
        this.fotos.remove(fotografia);
        fotografia.getAves().remove(this);
        return this;
    }

    public void setFotos(Set<Fotografia> fotografias) {
        this.fotos = fotografias;
    }

    public Set<Observatorio> getObservatorios() {
        return observatorios;
    }

    public Ave observatorios(Set<Observatorio> observatorios) {
        this.observatorios = observatorios;
        return this;
    }

    public Ave addObservatorio(Observatorio observatorio) {
        this.observatorios.add(observatorio);
        observatorio.getAves().add(this);
        return this;
    }

    public Ave removeObservatorio(Observatorio observatorio) {
        this.observatorios.remove(observatorio);
        observatorio.getAves().remove(this);
        return this;
    }

    public void setObservatorios(Set<Observatorio> observatorios) {
        this.observatorios = observatorios;
    }

    public Set<Avistamiento> getAvistamientos() {
        return avistamientos;
    }

    public Ave avistamientos(Set<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
        return this;
    }

    public Ave addAvistamiento(Avistamiento avistamiento) {
        this.avistamientos.add(avistamiento);
        avistamiento.getAves().add(this);
        return this;
    }

    public Ave removeAvistamiento(Avistamiento avistamiento) {
        this.avistamientos.remove(avistamiento);
        avistamiento.getAves().remove(this);
        return this;
    }

    public void setAvistamientos(Set<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ave)) {
            return false;
        }
        return id != null && id.equals(((Ave) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ave{" +
            "id=" + getId() +
            ", nombreComun='" + getNombreComun() + "'" +
            ", nombreCientifico='" + getNombreCientifico() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", sonido='" + getSonido() + "'" +
            ", sonidoContentType='" + getSonidoContentType() + "'" +
            "}";
    }
}
