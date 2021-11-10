package com.ratnikov.bankcard.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    @NotEmpty(message = "Укажите наименование категории")
    private String name;

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }
}
