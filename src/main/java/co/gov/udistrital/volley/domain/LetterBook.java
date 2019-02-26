package co.gov.udistrital.volley.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A LetterBook.
 */
@Entity
@Table(name = "letter_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LetterBook implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "incoming_mail_number", nullable = false)
    private Long incomingMailNumber;

    @Column(name = "incoming_mail_date")
    private LocalDate incomingMailDate;

    @OneToMany(mappedBy = "letterBook")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Letter> letters = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIncomingMailNumber() {
        return incomingMailNumber;
    }

    public LetterBook incomingMailNumber(Long incomingMailNumber) {
        this.incomingMailNumber = incomingMailNumber;
        return this;
    }

    public void setIncomingMailNumber(Long incomingMailNumber) {
        this.incomingMailNumber = incomingMailNumber;
    }

    public LocalDate getIncomingMailDate() {
        return incomingMailDate;
    }

    public LetterBook incomingMailDate(LocalDate incomingMailDate) {
        this.incomingMailDate = incomingMailDate;
        return this;
    }

    public void setIncomingMailDate(LocalDate incomingMailDate) {
        this.incomingMailDate = incomingMailDate;
    }

    public Set<Letter> getLetters() {
        return letters;
    }

    public LetterBook letters(Set<Letter> letters) {
        this.letters = letters;
        return this;
    }

    public LetterBook addLetters(Letter letter) {
        this.letters.add(letter);
        letter.setLetterBook(this);
        return this;
    }

    public LetterBook removeLetters(Letter letter) {
        this.letters.remove(letter);
        letter.setLetterBook(null);
        return this;
    }

    public void setLetters(Set<Letter> letters) {
        this.letters = letters;
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
        LetterBook letterBook = (LetterBook) o;
        if (letterBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), letterBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LetterBook{" +
            "id=" + getId() +
            ", incomingMailNumber=" + getIncomingMailNumber() +
            ", incomingMailDate='" + getIncomingMailDate() + "'" +
            "}";
    }
}
