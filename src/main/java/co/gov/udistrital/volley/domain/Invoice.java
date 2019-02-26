package co.gov.udistrital.volley.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ammount", nullable = false)
    private Double ammount;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Membership fee;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmmount() {
        return ammount;
    }

    public Invoice ammount(Double ammount) {
        this.ammount = ammount;
        return this;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Membership getFee() {
        return fee;
    }

    public Invoice fee(Membership membership) {
        this.fee = membership;
        return this;
    }

    public void setFee(Membership membership) {
        this.fee = membership;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Invoice payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Invoice addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setInvoice(this);
        return this;
    }

    public Invoice removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setInvoice(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
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
        Invoice invoice = (Invoice) o;
        if (invoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", ammount=" + getAmmount() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            "}";
    }
}
