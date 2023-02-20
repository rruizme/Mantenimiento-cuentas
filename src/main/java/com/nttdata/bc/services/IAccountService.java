package com.nttdata.bc.services;

import java.util.List;

import com.nttdata.bc.models.Account;

public interface IAccountService extends ICRUD<Account, Integer> {
    List<Account> findDebitCardId(Integer debitCardId);
}
