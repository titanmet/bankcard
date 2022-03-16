package com.ratnikov.bankcard.model;

import com.ratnikov.bankcard.mapper.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator")
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "name")
    @NotEmpty(message = "Укажите наименование категории")
    private String name;

    public Category(Long id) {
        this.id = id;
    }

    @Default
    public Category(String name) {
        this.name = name;
    }
}