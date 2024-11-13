package com.example.sberstart_test_step.model.base;

import com.example.sberstart_test_step.utils.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity {
    @Column(name = "number")
    String number;

    @Column(name = "release")
    LocalDate release;

    @ManyToOne
    Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    List<Card> card;

    public Account(UUID id, String number, LocalDate release, Customer customer, List<Card> card, LocalDate created,
                   LocalDate updated, Status status) {
        super(id, created, updated, status);
        this.number = number;
        this.release = release;
        this.customer = customer;
        this.card = card;
    }
}
