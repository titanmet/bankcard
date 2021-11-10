package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.model.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchedulerService {

    private static final String CRON = "*/59 * * * * *";

    private final CardService cardService;
    private final EmailService emailService;

    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        List<Card> cards = cardService.getCardExpirationDate(month, day ,year);
        if (!cards.isEmpty()) {
            cards.forEach(card -> {
                try {
                    String message = "Уважаемый " + card.getCustomer().getCustomerName() + " , " +
                            "истекает срок действия банковской карты номер : " +card.getCardNumber();
                    emailService.send(card.getCustomer().getEmail(), "Истекате срок действия банковской карты ", message);
                    log.info("Email have been sent. User id: {}, Date: {}", card.getCustomer().getId(), date);
                } catch (Exception e) {
                    log.error("Email can't be sent.User's id: {}, Error: {}", card.getCustomer().getId(), e.getMessage());
                    log.error("Email can't be sent", e);
                }
            });
        }
    }

}