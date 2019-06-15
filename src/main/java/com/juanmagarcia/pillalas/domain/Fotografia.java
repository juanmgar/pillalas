package com.juanmagarcia.pillalas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    @JsonIgnoreProperties("fotografias")
    private Avistamiento avistamiento;

    @ManyToOne
    @JsonIgnoreProperties("fotografias")
    private Observatorio observatorio;

    @ManyToOne
    @JsonIgnoreProperties("fotografias")
    private User autor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fotografia_ave",
               joinColumns = @JoinColumn(name = "fotografia_id", referencedColumnName = "id"),
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

    public Avistamiento getAvistamiento() {
        return avistamiento;
    }

    public Fotografia avistamiento(Avistamiento avistamiento) {
        this.avistamiento = avistamiento;
        return this;
    }

    public void setAvistamiento(Avistamiento avistamiento) {
        this.avistamiento = avistamiento;
    }

    public Observatorio getObservatorio() {
        return observatorio;
    }

    public Fotografia observatorio(Observatorio observatorio) {
        this.observatorio = observatorio;
        return this;
    }

    public void setObservatorio(Observatorio observatorio) {
        this.observatorio = observatorio;
    }

    public User getAutor() {
        return autor;
    }

    public Fotografia autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public Set<Ave> getAves() {
        return aves;
    }

    public Fotografia aves(Set<Ave> aves) {
        this.aves = aves;
        return this;
    }

    public Fotografia addAve(Ave ave) {
        this.aves.add(ave);
        ave.getFotos().add(this);
        return this;
    }

    public Fotografia removeAve(Ave ave) {
        this.aves.remove(ave);
        ave.getFotos().remove(this);
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
