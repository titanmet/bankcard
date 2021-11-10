package com.ratnikov.bankcard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "card_number")
    @NotEmpty(message = "Введите номер карты")
    private String cardNumber;
    private String creationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String pin;
    private String balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Card() {}

    public Card(Integer id, String cardNumber, String creationDate, LocalDate expirationDate, String pin, String balance, Customer customer) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.balance = balance;
        this.customer = customer;
    }

    public Card(String cardNumber, String creationDate, LocalDate expirationDate, String pin, String balance, Customer customer) {
        this.cardNumber = cardNumber;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.balance = balance;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Карта № " + cardNumber + " ";
    }
}
