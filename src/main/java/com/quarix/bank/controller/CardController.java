package com.quarix.bank.controller;

import com.quarix.bank.model.*;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.function.MonetaryQueries;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.List;

@Api(value = "BankController", description = "APIs конвертации валют")
@RestController
public class CardController {

    private static final Logger statLogger = LogManager.getLogger(Stat.class);
    private static final Stat stat = new Stat();

    @ApiOperation(value = "Конвертация валюты")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = ConvertUI.class)})
    @PostMapping("/exchange")
    public String exchange(@ApiParam(value = "Тело перевода", required = true) @RequestBody ConvertUI cui) {
        if (cui.getCount() > 0) {
            statLogger.info("Получено на вход - " + cui.getCount() + " " + cui.getCurrencyFrom());
            MonetaryAmount input = Monetary.getDefaultAmountFactory().setCurrency(cui.getCurrencyFrom())
                    .setNumber(cui.getCount()).create();

            CurrencyConversion output = MonetaryConversions.getConversion(cui.getCurrencyTo());
            //Write to stat if conversion from USD
            if (cui.getCurrencyFrom().equals("USD")) {
                addStatData(cui.getUserId(), cui.getCount(), cui.getCurrencyFrom(), cui.getCurrencyTo());
                System.out.println("============USD:" + input.query(MonetaryQueries.convertMinorPart()));
            }

            //Write to stat if conversion to usd and return result
            if (cui.getCurrencyTo().equals("USD")) {
                MonetaryAmount usdOutput = input.with(output);
                addStatData(cui.getUserId(), usdOutput.query(MonetaryQueries.convertMinorPart()), cui.getCurrencyFrom(), cui.getCurrencyTo());
                String res = usdOutput.toString();
                return res;
            } else {
                //Convert to usd and save to stat
                CurrencyConversion toUSD = MonetaryConversions.getConversion("USD");
                addStatData(cui.getUserId(), input.with(toUSD).query(MonetaryQueries.convertMinorPart()), cui.getCurrencyFrom(), cui.getCurrencyTo());
            }
            String res = input.with(output).toString();
            statLogger.info("Сконвертировано в - " + res);
            return res;
        }
        statLogger.warn("На вход получено меньше 0 денежных единиц");
        return "Count must be greater than 0";
    }

    //Add user's operation to stat
    private void addStatData(long userId, long value, String from, String to) {
        statLogger.info("Для пользователя [id="+userId+"] добавлено " + (value/100) + " USD");
        stat.addUserData(userId, value);
        stat.addRate(from, to);
        stat.incCount();
    }

    @ApiOperation(value = "Общая статистика")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = String.class)})
    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats() {
        return new StatUI(stat).toString();
    }

    @ApiOperation(value = "Статистика по пользователям с переводом от 10к")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = User.class)})
    @RequestMapping(value = "/stats/Max", method = RequestMethod.GET)
    public List<User> statsMax() {
        StatUI statUI = new StatUI(stat);
        return statUI.getUsers10k();
    }


    @ApiOperation(value = "Статистика по пользователям с суммарным переводом от 100к")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = User.class)})
    @RequestMapping(value = "/stats/Sum", method = RequestMethod.GET)
    public List<User> statsSum() {
        StatUI statUI = new StatUI(stat);
        return statUI.getUsersSummary100k();
    }

    @ApiOperation(value = "Статистика по валютам")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = CurrencyRate.class)})
    @RequestMapping(value = "/stats/Ratings", method = RequestMethod.GET)
    public List<CurrencyRate> statsRatings() {
        StatUI statUI = new StatUI(stat);
        return statUI.getRating();
    }
}