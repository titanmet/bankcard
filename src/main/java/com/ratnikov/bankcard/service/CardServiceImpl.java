package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.model.Card;
import com.ratnikov.bankcard.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public Page<Card> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.cardRepository.findAll(pageable);
    }

    @Override
    public List<Card> getCardExpirationDate(int month, int day, int year) {
        return cardRepository.findByMatchMonthAndMatchDay(month, day, year);
    }

    @Override
    public List<Card> getByKeyword(String keyword) {
        return cardRepository.findByKeyword(keyword);
    }

    @Override
    public Card findCardByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }
}
