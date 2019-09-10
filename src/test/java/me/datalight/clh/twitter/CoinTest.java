package me.datalight.clh.twitter;

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

@Feature("Таблица twitter.coin")
@Severity(SeverityLevel.CRITICAL)
@Disabled
public class CoinTest extends CHTest {

    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForEmptyTest() {
        List<String> stringRows = Arrays.asList("link", "symbol");
        stringRows.forEach(stringRow -> {
            boolean b = ch.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("processing_date", "processed_dttm");
        dateRows.forEach(dateRow -> {
            boolean b = ch.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkIntForInvalidOrNull() {
        List<String> numberRows = Arrays.asList("twitts", "followers", "following", "likes");
        numberRows.forEach(numberRow -> {
            boolean b = ch.checkNumbersForNegative(tableName, numberRow);
            assertNumbersForNagative(b, numberRow);
        });
        numberRows.forEach(notNullNumberRow -> {
            boolean b = ch.checkForNull(tableName, notNullNumberRow);
            assertNulls(b, notNullNumberRow);
        });
    }

    @Test
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("symbol", "link", "processing_date");
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
        assertToday(count, 620);
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
