package com.juanmagarcia.pillalas.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fotografia.
 */
@Entity
@Table(name = "fotografia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fotografia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "fichero")
    private byte[] fichero;

    @Column(name = "fichero_content_type")
    private String ficheroContentType;

    @ManyToOne
    @JsonIgnoreProperties("fotos")
    private Ave ave;

    @ManyToMany(mappedBy = "fotos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Avistamiento> avistamientos = new HashSet<>();

    @ManyToMany(mappedBy = "observatorios")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Observatorio> observatorios = new HashSet<>();

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

    public Fotografia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public Fotografia fichero(byte[] fichero) {
        this.fichero = fichero;
        return this;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
    }

    public String getFicheroContentType() {
        return ficheroContentType;
    }

    public Fotografia ficheroContentType(String ficheroContentType) {
        this.ficheroContentType = ficheroContentType;
        return this;
    }

    public void setFicheroContentType(String ficheroContentType) {
        this.ficheroContentType = ficheroContentType;
    }

    public Ave getAve() {
        return ave;
    }

    public Fotografia ave(Ave ave) {
        this.ave = ave;
        return this;
    }

    public void setAve(Ave ave) {
        this.ave = ave;
    }

    public Set<Avistamiento> getAvistamientos() {
        return avistamientos;
    }

    public Fotografia avistamientos(Set<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
        return this;
    }

    public Fotografia addAvistamiento(Avistamiento avistamiento) {
        this.avistamientos.add(avistamiento);
        avistamiento.getFotos().add(this);
        return this;
    }

    public Fotografia removeAvistamiento(Avistamiento avistamiento) {
        this.avistamientos.remove(avistamiento);
        avistamiento.getFotos().remove(this);
        return this;
    }

    public void setAvistamientos(Set<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
    }

    public Set<Observatorio> getObservatorios() {
        return observatorios;
    }

    public Fotografia observatorios(Set<Observatorio> observatorios) {
        this.observatorios = observatorios;
        return this;
    }

    public Fotografia addObservatorio(Observatorio observatorio) {
        this.observatorios.add(observatorio);
        observatorio.getObservatorios().add(this);
        return this;
    }

    public Fotografia removeObservatorio(Observatorio observatorio) {
        this.observatorios.remove(observatorio);
        observatorio.getObservatorios().remove(this);
        return this;
    }

    public void setObservatorios(Set<Observatorio> observatorios) {
        this.observatorios = observatorios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fotografia)) {
            return false;
        }
        return id != null && id.equals(((Fotografia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fotografia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fichero='" + getFichero() + "'" +
            ", ficheroContentType='" + getFicheroContentType() + "'" +
            "}";
    }
}
