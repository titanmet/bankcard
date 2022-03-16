package com.ratnikov.bankcard.model;

import com.ratnikov.bankcard.mapper.Default;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Card {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "card_sequence",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "number")
    @NotNull(message = "Введите номер карты")
    private Integer number;
    @NotNull(message = "Введите дату создания карты")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @NotNull(message = "Введите дату окончания действия")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    @NotNull(message = "Введите пин код")
    private Integer pin;
    @NotNull(message = "Введите баланс")
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CARD"))
    private Customer customer;

    public Card() {
    }

    public Card(Long id, Integer number, LocalDate creationDate, LocalDate expirationDate, Integer pin, BigDecimal balance, Customer customer) {
        this.id = id;
        this.number = number;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.balance = balance;
        this.customer = customer;
    }

    @Default
    public Card(Integer number, LocalDate creationDate, LocalDate expirationDate, Integer pin, BigDecimal balance, Customer customer) {
        this.number = number;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.balance = balance;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Карта № " + number + " ";
    }
}
