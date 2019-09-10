package me.datalight.prosphero.coinmarketcap;

import io.qameta.allure.*;
import me.datalight.prosphero.PGTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица prosphero coinmarketcap.coin")
@Severity(SeverityLevel.NORMAL)
@DisplayName("prosphero coinmarketcap.coin")
public class CoinTest extends PGTest {


    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("id", "title", "symbol");
        stringRows.forEach(stringRow -> {
            boolean b = pg.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("date_update");
        dateRows.forEach(dateRow -> {
            boolean b = pg.checkForNull(tableName, dateRow);
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
            boolean b = pg.checkNumbersForNegative(tableName, numberRow);
            assertNumbersForNagative(b, numberRow);
        });
        notNullNumberRows.forEach(notNullNumberRow -> {
            boolean b = pg.checkForNull(tableName, notNullNumberRow);
            assertNulls(b, notNullNumberRow);
        });
    }

    @Test
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("id", "title", "symbol", "date_update");
        String keys = String.join(", ", primaryKeys);
        boolean b = pg.checkDoubles(tableName, keys, "");
        assertDoubles(b, keys);
    }

    @Test
    @Story("Данные за сегодня")
    @DisplayName(today)
    void checkTodayTest() {
        String group = "id, date_trunc('day', date_update)";
        int count = pg.checkDate(tableName, group, "date_update", "");
        assertToday(count, 2000);
    }

    @Test
    @Story("Относительная разница в данных за вчера и сегодня")
    @DisplayName(diff)
    void checkDiffTest() {
        String group = "id, date_trunc('day', date_update)";
        int tt = pg.checkDate(tableName, group, "date_update", "");
        int yy = pg.checkDate(tableName, group, "date_update", "-1");
        assertDiff(tt, yy);
    }
}
