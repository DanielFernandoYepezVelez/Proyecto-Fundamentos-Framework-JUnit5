package com.example.junit.app.models;

import com.example.junit.app.exceptions.MoneyInsufficientException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private String person;
    private BigDecimal balance;
    private Bank bank;

    public Account() {

    }

    public Account(String person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void debito(BigDecimal amount) {
        /* BigDecimal Es Inmutable (SIEMPRE CREA UNA NUEVA INSTANCIA) */
        // this.balance = this.balance.subtract(amount);
        BigDecimal newBalance = this.balance.subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new MoneyInsufficientException("Money Insufficient");
        }

        this.balance = newBalance;
    }

    public void credito(BigDecimal amount) {
        /* BigDecimal Es Inmutable (SIEMPRE CREA UNA NUEVA INSTANCIA) */
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(person, account.person) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, balance);
    }
}