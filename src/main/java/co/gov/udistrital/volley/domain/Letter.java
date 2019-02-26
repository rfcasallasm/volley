package co.gov.udistrital.volley.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Letter.
 */
@Entity
@Table(name = "letter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Letter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "sex", nullable = false)
    private Boolean sex;

    @Column(name = "telephone_number")
    private Long telephoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private PostalAddress postalAddress;

    @ManyToOne
    @JsonIgnoreProperties("letters")
    private LetterBook letterBook;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public Letter firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public Letter surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Letter birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean isSex() {
        return sex;
    }

    public Letter sex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Long getTelephoneNumber() {
        return telephoneNumber;
    }

    public Letter telephoneNumber(Long telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(Long telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public Letter postalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public LetterBook getLetterBook() {
        return letterBook;
    }

    public Letter letterBook(LetterBook letterBook) {
        this.letterBook = letterBook;
        return this;
    }

    public void setLetterBook(LetterBook letterBook) {
        this.letterBook = letterBook;
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
        Letter letter = (Letter) o;
        if (letter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), letter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Letter{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", surname='" + getSurname() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", sex='" + isSex() + "'" +
            ", telephoneNumber=" + getTelephoneNumber() +
            "}";
    }
}
