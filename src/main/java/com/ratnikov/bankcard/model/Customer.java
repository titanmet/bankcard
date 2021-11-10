package com.ratnikov.bankcard.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_name")
    @NotEmpty(message = "Укажите Ф.И.О клиента")
    private String customerName;
    @Column(name = "phone")
    @Pattern(regexp ="^\\+?[0-9. ()-]{7,25}$", message = "Введите действительный телефонный номер")
    @NotEmpty(message = "Введите телефонный номер")
    private String phone;
    @Email(message = "Укажите действительный Email")
    @NotEmpty(message = "Введите email")
    private String email;
    private String dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();

    public Customer(Integer id, String customerName, String phone, @Email String email, String dateOfBirth, Category category) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
    }


    public void addCards(String cardNumber, String creationDate, LocalDate expirationDate, String pin, String balance) {
        if(!cardNumber.isEmpty()){
            this.cards.add(new Card(cardNumber, creationDate, expirationDate, pin, balance, this));
        }
    }

    public void setCard(Integer id, String cardNumber, String creationDate, LocalDate expirationDate, String pin, String balance) {
        this.cards.add(new Card(id, cardNumber, creationDate, expirationDate, pin, balance, this));
    }
}
