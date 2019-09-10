package me.datalight.prosphero.telegram;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import me.datalight.prosphero.PGTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Feature("Таблица prosphero telegram.message_tonal_tf_idf")
@Severity(SeverityLevel.NORMAL)
@DisplayName("prosphero telegram.message_tonal_tf_idf")
public class Message_tonal_tf_idfTest extends PGTest {


    @Test
    @Story("Строки")
    @DisplayName(string)
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("symbol", "title", "name_chat", "key_coin");
        stringRows.forEach(stringRow -> {
            boolean b = pg.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }

    @Test
    @Story("Даты")
    @DisplayName(dates)
    void checkDatesForNullTest() {
        List<String> dateRows = Arrays.asList("processing_dttm");
        dateRows.forEach(dateRow -> {
            boolean b = pg.checkForNull(tableName, dateRow);
            assertNulls(b, dateRow);
        });
    }

    @Test
    @Story("Числа")
    @DisplayName(numbers)
    void checkNumbersForInvalidOrNullTest() {
        List<String> notNullNumberRows = Arrays.asList("tonal", "id", "ball");
        notNullNumberRows.forEach(notNullNumberRow -> {
            boolean b = pg.checkForNull(tableName, notNullNumberRow);
            assertNulls(b, notNullNumberRow);
        });
    }

    @Test
    @Story("Дубли")
    @DisplayName(doubles)
    void checkDoublesTest() {
        List<String> primaryKeys = Arrays.asList("title", "symbol", "name_chat", "processing_dttm");
        String keys = String.join(", ", primaryKeys);
        boolean b = pg.checkDoubles(tableName, keys, "");
        assertDoubles(b, keys);
    }

    @Test
    @Story("Данные за сегодня")
    @DisplayName(today)
    void checkTodayTest() {
        String group = "symbol, date_trunc('day', processing_dttm)";
        int count = pg.checkDate(tableName, group, "processing_dttm", "");
        assertToday(count, 10);
    }

    @Test
    @Story("Данные за сегодня для BTC, ETH, XRP")
    @DisplayName("есть данные для BTC, ETH, XRP")
    void checkTodayBTCTest() {
        List<String> symbols = Arrays.asList("btc", "eth", "xrp");
        symbols.forEach(symbol -> {
            int count = pg.selectCount("" +
                    "select symbol, count(*) as count\n" +
                    "from telegram.message_tonal_tf_idf\n" +
                    "where symbol = '" + symbol + "'\n" +
                    "and date_trunc('day', processing_dttm)=current_date\n" +
                    "group by symbol;");
            assertTodayCoins(count, symbol, 1);
        });
    }
}
