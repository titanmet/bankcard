package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.CardDTO;
import com.ratnikov.bankcard.mapper.CardMapper;
import com.ratnikov.bankcard.model.Card;
import com.ratnikov.bankcard.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public Page<CardDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.cardRepository.findAll(pageable)
                .map(cardMapper::cardToDTO);
    }

    @Override
    public List<CardDTO> getCardExpirationDate(int month, int day, int year) {
        return cardRepository.findByMatchMonthAndMatchDay(month, day, year)
                .stream().map(cardMapper::cardToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getByKeyword(String keyword) {
        return cardRepository.findByKeyword(keyword)
                .stream()
                .map(cardMapper::cardToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO findByCardNumber(Integer number) {
        Card card = cardRepository.findByNumber(number);
        return cardMapper.cardToDTO(card);
    }

    @Override
    public CardDTO findByCardId(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        return cardMapper.cardToDTO(card);
    }

    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll()
                .stream()
                .map(cardMapper::cardToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Card card) {
        cardRepository.save(card);
    }
}