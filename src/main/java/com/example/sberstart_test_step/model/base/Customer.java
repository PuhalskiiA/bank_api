package com.example.sberstart_test_step.model.base;

import com.example.sberstart_test_step.utils.Role;
import com.example.sberstart_test_step.utils.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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
public class Customer extends BaseEntity {
    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "passport")
    String passport;

    @Column(name = "birthday")
    LocalDate birthday;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    List<Account> accounts;

    public Customer(UUID id, String firstName, String lastName, String email, String phone, String passport,
                    LocalDate birthday, Role role, List<Account> accounts, LocalDate created, LocalDate updated, Status status) {
        super(id, created, updated, status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.birthday = birthday;
        this.role = role;
        this.accounts = accounts;
    }
}
