package com.nttdata.bc.services.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.jboss.logging.Logger;

import com.nttdata.bc.exceptions.BadRequestException;
import com.nttdata.bc.exceptions.NotFoundException;
import com.nttdata.bc.models.Account;
import com.nttdata.bc.models.DebitCard;
import com.nttdata.bc.repositories.AccountRepository;
import com.nttdata.bc.repositories.DebitCardRepository;
import com.nttdata.bc.services.IDebitCardService;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DebitCardServiceImpl implements IDebitCardService {
    @Inject
    Logger logger;

    @Inject
    DebitCardRepository repository;

    @Inject
    AccountRepository accountRepository;

    @Override
    public DebitCard insert(DebitCard obj) {
        Account account = this.accountRepository.findById(obj.getAccountId());
        if (account == null) {
            throw new NotFoundException("La cuenta con id: " + obj.getAccountId() + ", no existe.");
        }

        if (account.getIsMain() != true) {
            throw new BadRequestException("La cuenta con id: " + obj.getAccountId() + ", no es la principal.");
        }

        if (account.getIsMain() && account.getDebitCard() != null) {
            throw new BadRequestException(
                    "La cuenta principal con id: " + obj.getAccountId() + ", ya tiene asociada una tarjeta.");
        }

        obj.setCardNumber(this.generateCardNumber());
        obj.setPin(this.generatePin());
        obj.setExpirationDate(this.generateExpirationDate());
        obj.setCardValidationCode(this.generateCardValidationCode());
        obj.setIsActive(true);
        obj.setCreatedAt(Instant.now());
        this.repository.persist(obj);

        account.setDebitCard(obj);
        account.setUpdateddAt(Instant.now());

        return obj;
    }

    @Override
    public DebitCard update(DebitCard obj) {
        DebitCard debitCard = this.findById(obj.getDebitCardId());
        debitCard.setPin(obj.getPin());
        debitCard.setUpdateddAt(Instant.now());

        return debitCard;
    }

    @Override
    public List<DebitCard> findAll() {
        return this.repository.listAll(Sort.by("debitCardId")); // [RRM] - ADICIÓN - ordena por ID
    }

    @Override
    public DebitCard findById(Integer id) {
        DebitCard debitCard = this.repository.findById(id);
        if (debitCard == null) {
            throw new NotFoundException("La tarjeta de débito con id: " + id.toString() + ", no existe.");
        }
        return debitCard;
    }

    @Override
    public void delete(Integer id) {
        DebitCard debitCard = this.findById(id);
        debitCard.setIsActive(false);
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString();
    }

    private String generatePin() {
        Random r = new Random();
        Integer pin = r.nextInt(9000) + 1000;
        return pin.toString();
    }

    private String generateCardValidationCode() {
        Random r = new Random();
        Integer pin = r.nextInt(900) + 100;
        return pin.toString();
    }

    private String generateExpirationDate() {
        LocalDate date = LocalDate.now();
        String month = ("0" + date.getMonthValue()).length() == 2 ? ("0" + date.getMonthValue())
                : String.valueOf(date.getMonthValue());
        int year = date.getYear() + 5;
        String expirationDate = month + "/" + String.valueOf(year).substring(2, 4);

        return expirationDate;
    }

}
