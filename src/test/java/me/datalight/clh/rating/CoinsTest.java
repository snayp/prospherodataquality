package me.datalight.clh.rating;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import me.datalight.clh.CHTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица rating.coin")
@Severity(SeverityLevel.TRIVIAL)
public class CoinsTest extends CHTest {

    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("rating", "symbol");
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
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("symbol", "processing_date");
        String keys = String.join(", ", primaryKeys);
        boolean b = ch.checkDoubles(tableName, keys, "");
        assertDoubles(b, keys);
    }
}
