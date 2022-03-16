package com.ratnikov.bankcard.mapper;

import com.ratnikov.bankcard.dto.CardDTO;
import com.ratnikov.bankcard.model.Card;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDTO cardToDTO(Card card);

    List<CardDTO> toCardDTOs(List<Card> cards);

    Card cardToModel(CardDTO cardDTO);

    CardDTO cardToDTO(Optional<Card> card);
}
