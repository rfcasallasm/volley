package co.gov.udistrital.volley.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MembershipCard.
 */
@Entity
@Table(name = "membership_card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MembershipCard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "membership_number", nullable = false)
    private Long membershipNumber;

    @Column(name = "commencement_date")
    private LocalDate commencementDate;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(unique = true)
    private PostalAddress postalAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMembershipNumber() {
        return membershipNumber;
    }

    public MembershipCard membershipNumber(Long membershipNumber) {
        this.membershipNumber = membershipNumber;
        return this;
    }

    public void setMembershipNumber(Long membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public LocalDate getCommencementDate() {
        return commencementDate;
    }

    public MembershipCard commencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
        return this;
    }

    public void setCommencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getName() {
        return name;
    }

    public MembershipCard name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public MembershipCard birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public MembershipCard postalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
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
        MembershipCard membershipCard = (MembershipCard) o;
        if (membershipCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membershipCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembershipCard{" +
            "id=" + getId() +
            ", membershipNumber=" + getMembershipNumber() +
            ", commencementDate='" + getCommencementDate() + "'" +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
