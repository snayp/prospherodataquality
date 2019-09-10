package me.datalight.clh;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static me.datalight.Utils.parseDateGMT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class RealTime5mTest extends CHTest {


    private List<String> exchanges = Arrays.asList("'bitmex'", "'binance'","'bitfinex'");

    @Disabled
    @Test
    void testPricesCoin() {
        exchanges.forEach(exchange -> {
            String l = ch.selectLast(ch.lastUpdateWhere("`prices.coin`",   "created_at", "WHERE exchange = " + exchange));
            Date ld =  new Date();
            try {
                ld = parseDateGMT(l, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            log.info("`prices.coin` для " + exchange + "обновлена в " + ld.getTime()/1000);
            log.info("сейчас " + String.valueOf(unixTime));
            try {
                assertThat(Long.valueOf(ld.getTime()/1000).doubleValue(), closeTo(unixTime, 360));
            } catch (AssertionError e) {
                session.sendMessage(channel_test, "Нет данных в таблице `prices.coin` больше 6 минут для " + exchange);
            }
        });
    }

    @Test
    void testCoinmarketcapCoin() {
        String l = ch.selectLast(ch.lastUpdate("`coinmarketcap.coin`",   "date_update"));
        Date ld =  new Date();
        try {
            ld = parseDateGMT(l, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("`coinmarketcap.coin` обновлена в " + (ld.getTime()/1000));
        log.info("сейчас " + String.valueOf(unixTime));
        try {
            assertThat(Long.valueOf(ld.getTime()/1000).doubleValue(), closeTo(unixTime, 660));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице `coinmarketcap.coin` больше 11 минут");
        }
    }
}
