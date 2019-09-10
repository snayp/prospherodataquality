package me.datalight.clh;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static me.datalight.Utils.parseDateGMT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class TelegaTest extends CHTest {

    @Test
    void testTelegaTonal() {
        String l = ch.selectLast(ch.lastUpdate("`telegram.message_tonal_tf_idf2`",   "processing_dttm"));
        Date ld =  new Date();
        try {
            ld = parseDateGMT(l, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("`telegram.message_tonal_tf_idf2` обновлена в " + (ld.getTime()/1000));
        log.info("сейчас " + unixTime);
        try {
            assertThat(Long.valueOf(ld.getTime()/1000).doubleValue(), closeTo(unixTime, 46800));
        } catch (AssertionError e) {
            session.sendMessage(channel_data, "Нет данных в таблице `telegram.message_tonal_tf_idf2` больше 13 часов");
        }
    }

    @Test
    void testTelegaMessages() {
        String l = ch.selectLast(ch.lastUpdate("`telegram.message_new2`",   "processing_dttm"));
        Date ld =  new Date();
        try {
            ld = parseDateGMT(l, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("`telegram.message_new2` обновлена в " + (ld.getTime()/1000));
        log.info("сейчас " + unixTime);
        try {
            assertThat(Long.valueOf(ld.getTime()/1000).doubleValue(), closeTo(unixTime, 46800));
        } catch (AssertionError e) {
            session.sendMessage(channel_data, "Нет данных в таблице `telegram.message_new2` больше 13 часов");
        }
    }
}
