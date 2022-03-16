package com.ratnikov.bankcard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator")
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name")
    @Length(min = 5, message = "Имя пользователя должно содержать не менее 5 символов")
    @NotEmpty(message = "Укажите имя пользователя")
    private String userName;
    @Column(name = "email")
    @Email(message = "Укажите действительный Email")
    @NotEmpty(message = "Введите email")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "Пароль должен содержать не менее 5 символов")
    @NotEmpty(message = "Введите пароль")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "Укажите ваше Имя")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "Укажите вашу Фамилию")
    private String lastName;
    @Column(name = "active")
    private Boolean active;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK_USER"), inverseJoinColumns = @JoinColumn(name = "role_id"), inverseForeignKey = @ForeignKey(name = "FK_ROLE"))
    private Set<Role> roles;
}