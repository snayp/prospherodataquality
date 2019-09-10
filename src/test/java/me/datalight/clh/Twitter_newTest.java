package me.datalight.clh;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица twitter_new")
@Severity(SeverityLevel.CRITICAL)
public class Twitter_newTest extends CHTest {

    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForEmptyTest() {
        List<String> stringRows = Arrays.asList("link", "author", "keyword");
        stringRows.forEach(stringRow -> {
            boolean b = ch.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("request_dttm", "create_dttm", "write_dttm", "write_date");
        dateRows.forEach(dateRow -> {
            boolean b = ch.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkIntForInvalidOrNull() {
        List<String> numberRows = Arrays.asList("id", "retweets", "likes");
        numberRows.forEach(numberRow -> {
            boolean b = ch.checkNumbersForNegative(tableName, numberRow);
            assertNumbersForNagative(b, numberRow);
        });
        numberRows.forEach(notNullNumberRow -> {
            boolean b = ch.checkForNull(tableName, notNullNumberRow);
            assertNulls(b, notNullNumberRow);
        });
    }

    @Disabled
    @Test
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("id", "author", "link", "keyword", "write_dttm", "request_dttm");
        String keys = String.join(", ", primaryKeys);
        boolean b = ch.checkDoubles(tableName, keys, "WHERE write_date > '2018-11-14'" + "\n");
        assertDoubles(b, keys);
    }

    @Test
    @Story("Данные за сегодня")
    @DisplayName(today)
    void checkTodayTest() {
        List<String> groupBy = Arrays.asList("keyword", "write_date");
        String group = String.join(", ", groupBy);
        int count = ch.checkDate(tableName, group, "write_date", "");
        assertToday(count, 1300);
    }


    @Test
    @Story("Данные за сегодня для BTC, ETH")
    @DisplayName("есть данные для BTC, ETH")
    void checkTodayBTCTest() {
        List<String> symbols = Arrays.asList("btc", "Bitcoin", "ETH");
        symbols.forEach(symbol -> {
            int count = ch.selectCount("" +
                    "select keyword, count(*) as count\n" +
                    "from twitter_new\n" +
                    "where keyword = '" + symbol + "'\n" +
                    "and write_date=today()\n" +
                    "group by keyword;");
            assertTodayCoins(count, symbol, 15000);
        });
    }

    @Test
    @Story("Данные за сегодня по всем монетам")
    @DisplayName("есть данные за сегодня по всем монетам")
    void checkFirst1000() {

    }
}
