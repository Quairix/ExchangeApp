package com.quarix.bank.model;

import java.util.Objects;
import java.util.Random;

public class User {
    private final long id;

    /**
     * Сумма всех конвертаций
     **/
    private double summary;

    /**
     * Масимальный объем за одну операцию
     **/
    private double max;

    public User() {
        this.id = new Random().nextInt(10000) & Integer.MAX_VALUE;
        this.summary = 0;
        this.max = 0;
    }

    public User(long id, double value) {
        this.id = id;
        this.summary = value;
        this.max = value;
    }

    public double getSummary() {
        return summary;
    }

    public long getId() {
        return id;
    }

    public void addCash(double value) {
        if (value > max) max = value;
        summary += value;
    }


    public double getMax() {
        return max;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return "[User id=" + this.id + ", Max=" + this.max + ", Sum=" + this.summary + "]";
    }
}
