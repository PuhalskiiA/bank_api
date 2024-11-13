package com.example.sberstart_test_step.repository;

import com.example.sberstart_test_step.model.base.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = "select accn.* from account as accn " +
            "join customer on customer.id = accn.customer_id " +
            "where customer.id = ?1 and accn.status = 'ACTIVE'", nativeQuery = true)
    List<Account> getAccountsByCustomerId(UUID customerId);

    @Query(value = "select * from account where account.id = ?1 and account.status = 'ACTIVE'", nativeQuery = true)
    Optional<Account> getAccountById(UUID accountId);

    @Modifying
    @Query(value = "insert into account (id, number, release, customer_id, created, updated, status) values (?, ?, ?, ?, ?, ?, ?)",
            nativeQuery = true)
    void createAccount(UUID id, String number, Date release, UUID customerId, Date created, Date updated, String status);

    @Modifying
    @Query(value = "update account set account.status = ?3, account.updated = ?2 " +
            "where account.id = ?1", nativeQuery = true)
    void deleteAccountById(UUID accountId, Date updated, String status);
}
