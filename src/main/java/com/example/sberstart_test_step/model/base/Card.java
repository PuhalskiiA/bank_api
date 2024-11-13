package com.example.sberstart_test_step.model.base;

import com.example.sberstart_test_step.utils.CardStatus;
import com.example.sberstart_test_step.utils.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card extends BaseEntity {
    @Column(name = "number")
    String number;

    @Column(name = "pin")
    String pin;

    @Column(name = "release")
    LocalDate release;

    @Column(name = "expired")
    LocalDate expired;

    @Column(name = "cvv")
    String cvv;

    @Column(name = "balance")
    BigDecimal balance;

    @ManyToOne
    Account account;

    @Column(name = "card_status")
    @Enumerated(EnumType.STRING)
    CardStatus cardStatus;

    public Card(UUID id, String number, String pin, LocalDate release, LocalDate expired, String cvv, BigDecimal balance,
                Account account, CardStatus cardStatus, LocalDate created, LocalDate updated, Status status) {
        super(id, created, updated, status);
        this.number = number;
        this.pin = pin;
        this.release = release;
        this.expired = expired;
        this.cvv = cvv;
        this.balance = balance;
        this.account = account;
        this.cardStatus = cardStatus;
    }
}
