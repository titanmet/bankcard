package com.ratnikov.bankcard.repository;

import com.ratnikov.bankcard.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findByCardNumber(String cardNumber);

    @Query(value = "SELECT * FROM card " +
            "WHERE card_number IS NOT NULL " +
            "AND extract(MONTH FROM expiration_date) = :m " +
            "AND extract(DAY FROM expiration_date) = :d " +
            "AND extract(YEAR FROM expiration_date) = :y ",
            nativeQuery = true)
    List<Card> findByMatchMonthAndMatchDay(@Param("m") int month, @Param("d") int day, @Param("y") int year);

    @Query(value = "select * from card c where c.card_number like %:keyword%", nativeQuery = true)
    List<Card> findByKeyword(@Param("keyword") String keyword);
}
