package com.quarix.bank.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ConvertUI", description = "Модель операции конвертации")
public class ConvertUI {
    @ApiModelProperty(notes = "Id пользователя")
    private final long userId;

    @ApiModelProperty(notes = "Валюта пользователя")
    private final String currencyFrom;

    @ApiModelProperty(notes = "Во что конвертирует")
    private final String currencyTo;

    @ApiModelProperty(notes = "Кол-во")
    private final long count;

    public ConvertUI(long userId, String currencyFrom, String currencyTo, long count) {
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public long getCount() {
        return count;
    }
}
