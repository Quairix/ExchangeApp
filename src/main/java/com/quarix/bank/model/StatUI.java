package com.quarix.bank.model;

import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StatUI {
    @ApiModelProperty(notes = "Список пользователей с максимальным объемом конвертации более 10к")
    private final List<User> users10k;

    @ApiModelProperty(notes = "Список пользователей с общим объемом конвертации более 100к")
    private final List<User> usersSummary100k;

    @ApiModelProperty(notes = "Статистика операций перевода из валюты в валюту отсортированная по убыванию")
    private final List<CurrencyRate> rating;

    @ApiModelProperty(notes = "Общее количество операций")
    private final long totalCount;

    public StatUI(Stat stat) {
        users10k = new ArrayList<>();
        usersSummary100k = new ArrayList<>();
        stat.getUsers().forEach(user -> {
            if (user.getSummary() >= 100000) usersSummary100k.add(user);
            if (user.getMax() >= 10000) users10k.add(user);
        });
        this.rating = new ArrayList<>();
        this.rating.addAll(stat.getRate());
        this.rating.sort(Comparator.comparingInt(CurrencyRate::getCount).reversed());
        this.totalCount = stat.getTotalCount();
    }

    public List<User> getUsers10k() {
        return users10k;
    }

    public List<User> getUsersSummary100k() {
        return usersSummary100k;
    }

    public List<CurrencyRate> getRating() {
        return rating;
    }

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(date);
        return dateStr + "\nNumber of conversions: " + totalCount +
                "\nCurrency rating: " + rating +
                "\nUsers with max more than 10k: " + users10k +
                "\nUsers with sum more than 100k: " + usersSummary100k;
    }
}
