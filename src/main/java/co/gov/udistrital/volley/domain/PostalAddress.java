package co.gov.udistrital.volley.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PostalAddress.
 */
@Entity
@Table(name = "postal_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PostalAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull
    @Column(name = "house_number", nullable = false)
    private Long houseNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "residence")
    private String residence;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public PostalAddress street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public PostalAddress houseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public PostalAddress zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getResidence() {
        return residence;
    }

    public PostalAddress residence(String residence) {
        this.residence = residence;
        return this;
    }

    public void setResidence(String residence) {
        this.residence = residence;
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
        PostalAddress postalAddress = (PostalAddress) o;
        if (postalAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postalAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostalAddress{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", houseNumber=" + getHouseNumber() +
            ", zipCode='" + getZipCode() + "'" +
            ", residence='" + getResidence() + "'" +
            "}";
    }
}
