package com.ratnikov.bankcard.service;

public interface EmailService {
    void send(String to, String title, String body);
}
