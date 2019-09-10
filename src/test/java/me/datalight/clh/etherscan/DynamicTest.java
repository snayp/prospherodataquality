package me.datalight.clh.etherscan;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import me.datalight.clh.CHTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица etherscan.dynamic")
@Severity(SeverityLevel.CRITICAL)
public class DynamicTest extends CHTest {

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
        List<String> numberRows = Arrays.asList("address", "transfer", "holders_3", "holders_5", "holders_10", "holders_25", "holders_50", "holders_100", "holders_250", "holders_500");
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
        List<String> primaryKeys = Arrays.asList("symbol", "processing_dttm");
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
        assertToday(count, 720);
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
