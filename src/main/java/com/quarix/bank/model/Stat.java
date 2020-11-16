package com.quarix.bank.model;

import java.util.HashSet;

/**
 * Класс статистики
 */
public class Stat {
    /**
     * Список конвертаций из валюты в валюту
     **/
    private final HashSet<CurrencyRate> ratings;

    /**
     * Список пользователей
     **/
    private final HashSet<User> users;

    /**
     * Количество операций
     **/
    private int totalCount;

    public Stat() {
        totalCount = 0;
        users = new HashSet<>();
        ratings = new HashSet<>();
    }

    public HashSet<CurrencyRate> getRate() {
        return ratings;
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void addUserData(long userId, double value) {
        for (User user : users) {
            if (user.getId() == userId) {
                user.addCash(value/100);
                return;
            }
        }
        users.add(new User(userId, value/100));
    }

    public void addRate(String from, String to) {
        CurrencyRate temp = new CurrencyRate(from, to);
        for (CurrencyRate rate : ratings) {
            if (rate.equals(temp)) {
                rate.add();
                return;
            }
        }
        ratings.add(temp);
    }

    public void incCount() {
        this.totalCount++;
    }
}
