package com.ratnikov.bankcard.dto;

import com.ratnikov.bankcard.model.Card;
import lombok.Data;

import java.util.Set;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String dateOfBirth;
    private CategoryDTO category;
    private Set<Card> cards;
}
