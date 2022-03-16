package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CardDTO;
import com.ratnikov.bankcard.model.Card;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CardService {
    Page<CardDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    List<CardDTO> getCardExpirationDate(int month, int day, int year);

    List<CardDTO> getByKeyword(String keyword);

    CardDTO findByCardNumber(Integer number);

    CardDTO findByCardId(Long id);

    List<CardDTO> findAll();

    void deleteById(Long id);

    void save(Card card);
}
