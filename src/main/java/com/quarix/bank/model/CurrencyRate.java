package com.quarix.bank.model;

import java.util.Objects;

/**
 * Класс валюта/валюта - количество операций
 */
public class CurrencyRate {
    private final String from;
    private final String to;
    private int count;

    public CurrencyRate(String from, String to) {
        this.from = from;
        this.to = to;
        count = 1;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }

    public void add() {
        this.count++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return from.equals(that.from) &&
                to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public String toString() {
        return from + "/" + to + " - " + count;
    }
}
