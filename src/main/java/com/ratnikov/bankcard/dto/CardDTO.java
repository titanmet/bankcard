package com.ratnikov.bankcard.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardDTO {
    private Long id;
    private Integer number;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private Integer pin;
    private BigDecimal balance;
    private CustomerDTO customer;
}
