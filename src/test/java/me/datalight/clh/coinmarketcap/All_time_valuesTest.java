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

@Feature("Вьюха coinmarketcap.all_time_values")
@Severity(SeverityLevel.BLOCKER)
public class All_time_valuesTest extends CHTest {

    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("symbol");
        stringRows.forEach(stringRow -> {
            boolean b = ch.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("processing_date", "processing_dttm");
        dateRows.forEach(dateRow -> {
            boolean b = ch.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkNumbersForInvalidOrNullTest() {
        List<String> numberRows = Arrays.asList("price_usd_max", "price_usd_min", "market_cap_usd_max", "market_cap_usd_min", "volume_usd_max", "volume_usd_min");
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
        List<String> primaryKeys = Arrays.asList("symbol", "processing_date");
        String keys = String.join(", ", primaryKeys);
        boolean b = ch.checkDoubles(tableName, keys, "");
        assertDoubles(b, keys);
    }

    @Test
    @Story("Данные за сегодня")
    @DisplayName(today)
    void checkTodayTest() {
        List<String> groupBy = Arrays.asList("symbol", "processing_date");
        String group = String.join(", ", groupBy);
        int count = ch.checkDate(tableName, group, "processing_date", "");
        assertToday(count, 2000);
    }

    @Test
    @Story("Относительная разница в данных за вчера и сегодня")
    @DisplayName(diff)
    void checkDiffTest() {
        List<String> groupBy = Arrays.asList("symbol", "processing_date");
        String group = String.join(", ", groupBy);
        int tt = ch.checkDate(tableName, group, "processing_date", "");
        int yy = ch.checkDate(tableName, group, "processing_date", "-1");
        assertDiff(tt, yy);
    }
}
