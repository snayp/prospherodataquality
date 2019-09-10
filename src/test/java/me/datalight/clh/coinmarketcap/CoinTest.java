package me.datalight.clh.coinmarketcap;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import me.datalight.clh.CHTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица coinmarketcap.coin")
@Severity(SeverityLevel.BLOCKER)
public class CoinTest extends CHTest {

    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("id", "title", "symbol");
        stringRows.forEach(stringRow -> {
            boolean b = ch.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("processing_date", "date_update");
        dateRows.forEach(dateRow -> {
            boolean b = ch.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkNumbersForInvalidOrNullTest() {
        List<String> numberRows = Arrays.asList("rank", "price_usd", "price_btc", "h_volume_usd", "market_cap_usd", "total_supply", "max_supply", "last_updated");
        List<String> notNullNumberRows = Arrays.asList("rank", "price_usd", "price_btc", "h_volume_usd", "market_cap_usd", "available_supply", "total_supply",
                "percent_change_1h", "percent_change_24h", "percent_change_7d", "last_updated");
        numberRows.forEach(numberRow -> {
            boolean b = ch.checkNumbersForNegative(tableName, numberRow);
            assertNumbersForNagative(b, numberRow);
        });
        notNullNumberRows.forEach(notNullNumberRow -> {
            boolean b = ch.checkForNull(tableName, notNullNumberRow);
            assertNulls(b, notNullNumberRow);
        });
    }

    @Disabled
    @Test
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("id", "title", "symbol", "date_update", "processing_date");
        String keys = String.join(", ", primaryKeys);
        boolean b = ch.checkDoubles(tableName, keys, "");
        assertDoubles(b, keys);
    }


    @Test
    @Story("Данные за сегодня")
    @DisplayName(today)
    void checkTodayTest() {
        List<String> groupBy = Arrays.asList("id", "processing_date");
        String group = String.join(", ", groupBy);
        int count = ch.checkDate(tableName, group, "processing_date", "");
        assertToday(count, 2000);
    }

    @Test
    @Story("Относительная разница в данных за вчера и сегодня")
    @DisplayName(diff)
    void checkDiffTest() {
        List<String> groupBy = Arrays.asList("id", "processing_date");
        String group = String.join(", ", groupBy);
        int tt = ch.checkDate(tableName, group, "processing_date", "");
        int yy = ch.checkDate(tableName, group, "processing_date", "-1");
        assertDiff(tt, yy);
    }
}
