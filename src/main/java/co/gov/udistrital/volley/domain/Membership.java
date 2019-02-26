package co.gov.udistrital.volley.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commencement_date")
    private LocalDate commencementDate;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @OneToOne
    @JoinColumn(unique = true)
    private MembershipCard card;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCommencementDate() {
        return commencementDate;
    }

    public Membership commencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
        return this;
    }

    public void setCommencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
    }

    public Double getFee() {
        return fee;
    }

    public Membership fee(Double fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public MembershipCard getCard() {
        return card;
    }

    public Membership card(MembershipCard membershipCard) {
        this.card = membershipCard;
        return this;
    }

    public void setCard(MembershipCard membershipCard) {
        this.card = membershipCard;
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
        Membership membership = (Membership) o;
        if (membership.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membership.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            ", commencementDate='" + getCommencementDate() + "'" +
            ", fee=" + getFee() +
            "}";
    }
}
