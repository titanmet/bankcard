package com.ratnikov.bankcard.model;

import com.ratnikov.bankcard.mapper.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator")
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "customer_sequence",
            allocationSize = 1)
    private Long id;
    @Column(name = "name")
    @NotEmpty(message = "Укажите Ф.И.О клиента")
    private String name;
    @Column(name = "phone")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Введите действительный телефонный номер")
    @NotEmpty(message = "Введите телефонный номер")
    private String phone;
    @Email(message = "Укажите действительный Email")
    @NotEmpty(message = "Введите email")
    private String email;
    private String dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_CUSTOMER"))
    private Category category;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();

    @Default
    public Customer(Long id, String name, String phone, @Email String email, String dateOfBirth, Category category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
    }

    public void addCards(Integer cardNumber, LocalDate creationDate, LocalDate expirationDate, Integer pin, BigDecimal balance) {
        if (Optional.ofNullable(cardNumber).orElse(0) != 0) {
            this.cards.add(new Card(cardNumber, creationDate, expirationDate, pin, balance, this));
        }
    }

    public void setCard(Long id, Integer cardNumber, LocalDate creationDate, LocalDate expirationDate, Integer pin, BigDecimal balance) {
        this.cards.add(new Card(id, cardNumber, creationDate, expirationDate, pin, balance, this));
    }
}