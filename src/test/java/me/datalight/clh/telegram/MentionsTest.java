package me.datalight.clh.telegram;

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

@Disabled
@Feature("Таблица telegram.mentions")
@Severity(SeverityLevel.CRITICAL)
public class MentionsTest extends CHTest {

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
        List<String> dateRows = Arrays.asList("processing_date");
        dateRows.forEach(dateRow -> {
            boolean b = ch.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkNumbersForInvalidOrNullTest() {
        List<String> numberRows = Arrays.asList("symbol_count", "positive_mentions", "negative_mentions");
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
        assertToday(count, 150);
    }
}
