package com.example.sberstart_test_step.repository;

import com.example.sberstart_test_step.model.base.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    @Modifying
    @Query(value = "insert into card (id, number, cvv, pin, release, expired, card_status, balance, account_id, created, updated, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void create(UUID id, String number, String cvc, String pin, Date release, Date expired, String cardStatus,
                BigDecimal balance, UUID accountId, Date created, Date updated, String status);

    @Modifying
    @Query(value = "update card set card.updated = ?2, card.status = ?3 where card.number = ?1", nativeQuery = true)
    void deleteByNumber(String number, Date updated, String status);

    @Modifying
    @Query(value = "update card set card.balance = ?2, card.updated = ?3 where card.number = ?1", nativeQuery = true)
    void updateBalance(String number, BigDecimal balance, Date updated);

    @Modifying
    @Query(value = "update card set pin = ?2, card.updated = ?3 where card.number = ?1", nativeQuery = true)
    void updatePin(String number, String pin, Date updated);

    @Query(value = "select crd.* from card as crd " +
            "join account on account.id = crd.account_id " +
            "where account.id = ?1 and crd.status = 'ACTIVE'", nativeQuery = true)
    List<Card> getCardsByAccountId(UUID accountId);

    @Query(value = "select crd.* from card as crd " +
            "join account on account.id = crd.account_id " +
            "join customer on customer.id = account.customer_id " +
            "where crd.number = ?1 and customer.id = ?2 and crd.status = 'ACTIVE'", nativeQuery = true)
    Optional<Card> getCard(String number, UUID customerId);
}
