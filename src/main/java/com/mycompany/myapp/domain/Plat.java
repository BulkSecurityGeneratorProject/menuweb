package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.TypePlat;

/**
 * A Plat.
 */
@Entity
@Table(name = "plat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypePlat type;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    private User createur;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "plat_tags",
               joinColumns = @JoinColumn(name="plats_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Plat name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypePlat getType() {
        return type;
    }

    public Plat type(TypePlat type) {
        this.type = type;
        return this;
    }

    public void setType(TypePlat type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Plat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreateur() {
        return createur;
    }

    public Plat createur(User user) {
        this.createur = user;
        return this;
    }

    public void setCreateur(User user) {
        this.createur = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Plat tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Plat addTags(Tag tag) {
        this.tags.add(tag);
        tag.getPlats().add(this);
        return this;
    }

    public Plat removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.getPlats().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plat plat = (Plat) o;
        if (plat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
