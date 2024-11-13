package com.example.sberstart_test_step.repository;

import com.example.sberstart_test_step.model.base.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Modifying
    @Query(value = "insert into customer (id, first_name, last_name, role, email, phone, passport, birthday, created, updated, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void createCustomer(UUID id, String firstName, String lastName, String role, String email, String phone,
                        String passport, Date birthday, Date created, Date updated, String status);

    @Modifying
    @Query(value = "update customer set customer.status = ?3, customer.updated = ?2 where customer.id = ?1", nativeQuery = true)
    void deleteCustomerById(UUID id, Date updated, String status);

    @Modifying
    @Query(value = "update customer set first_name = ?2, last_name = ?3, email = ?4, phone = ?5, passport = ?6, " +
            "birthday = ?7, customer.updated = ?8 where customer.id = ?1", nativeQuery = true)
    void updateCustomerById(UUID id, String firstName, String lastName, String email, String phone, String passport,
                            Date birthday, Date updated);

    @Query(value = "select * from customer where customer.id = ?1 and customer.status = 'ACTIVE'", nativeQuery = true)
    Optional<Customer> getCustomer(UUID id);

    @Query(value = "select * from customer where first_name = ?1 and last_name = ?2 and passport = ?3 and customer.status = 'ACTIVE'", nativeQuery = true)
    Optional<Customer> getCustomerByFirstNameAndLastNameAndPassport(String firstName, String lastName, String passport);

    @Query(value = "select * from customer where phone = ?1 and customer.status = 'ACTIVE'", nativeQuery = true)
    Optional<Customer> getCustomerByPhone(String phone);
}
