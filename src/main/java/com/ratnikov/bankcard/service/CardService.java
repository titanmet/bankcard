package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.model.Card;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CardService {
    Page<Card> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Card> getCardExpirationDate(int month, int day, int year);
    List<Card> getByKeyword(String keyword);
    Card findCardByCardNumber(String cardNumber);
}
