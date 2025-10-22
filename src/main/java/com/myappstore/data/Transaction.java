package com.myappstore.data;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String description;
    private double amount;

    public Transaction(LocalDate date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}