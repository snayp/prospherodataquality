package me.datalight.clh;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static me.datalight.Utils.parseDateGMT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class HourlyFieldsTest extends CHTest {

    @Test
    void testCoinmarketcapTotal_dominance() {
        String table = "`coinmarketcap.total_dominance`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm = new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testRatingAuto() {
        String table = "`rating.auto`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testCoinmarketcapCoin_btc() {
        String table = "`coinmarketcap.coin_btc`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "date_update"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testTelegramCoin_hype_mood_daily() {
        String table = "`telegram.coin_hype_mood_daily`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 36000));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше 10 часов!");
        }
    }

    private List<String> blockchains = Arrays.asList("'XLM'", "'BTC'","'ETH'","'NEO'","'TRX'");
    /*private List<String> numRow = Arrays.asList()*/

    @Test
    void testEthereum_goTransaction_calcBlockchains() {
        blockchains.forEach(blockchain -> {
            String table = "`ethereum_go.transaction_calc`";
            String curDttm = ch.selectMaxDttm(ch.maxDttmWhere(table, "processing_dttm", " WHERE symbol = " + blockchain));
            Date mddtm =  new Date();
            try {
                mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 7200));
            } catch (AssertionError e) {
                session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше 2 часов для " + blockchain);
            }
         });
    }

    @Test
    void testEthereum_goTransaction_calc() {
        String table = "`ethereum_go.transaction_calc`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }


    @Test
    void testEthereum_goTransaction_stat_usd_24h() {
        String table = "`ethereum_go.transaction_stat_usd_24h`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }


    @Test
    void testCurrentCoin() {
        String table = "`current.coin`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testTwitterHype() {
        String table = "`twitter.hype`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 7200));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше двух часов!");
        }
    }

    @Test
    void testAllCoin() {
        String table = "`all.coin`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testCoinmarketcapCoin() {
        String table = "`coinmarketcap.coin`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "date_update"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }



    @Test
    void testCoinmarketcapMayer() {
        String table = "`coinmarketcap.mayer`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }


    @Test
    void testCoinmarketcapСoin_calc() {
        String table = "`coinmarketcap.coin_calc`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "date_update"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testCoinmarketcapСoin_cvix_30d_1h() {
        String table = "`coinmarketcap.coin_cvix_30d_1h`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "calendar_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3900));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше 65 минут!");
        }
    }

    @Test
    void testEthereumToken_exist() {
        String table = "`ethereum.token_exist`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testPricesBuy_sell() {
        String table = "`prices.buy_sell`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testPriceEth_xrp_bnb() {
        String table = "`prices.eth_xrp_bnb`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testTelegramHype_chng() {
        String table = "`telegram.hype_chng`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testTwitterHype_chng() {
        String table = "`twitter.hype_chng`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Disabled
    @Test
    void testWikiCoin() {
        String table = "`wiki.coin`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }


    @Disabled
    @Test
    void testCoinmarketcapVolume_change_curr() {
        String table = "`coinmarketcap.volume_change_curr`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }


    @Disabled
    @Test
    void testCoinmarketcapAll_time_values() {
        String table = "`coinmarketcap.all_time_values`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 3600));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }
    }

    @Test
    void testTelegramHypeGlobalDaily() {
        String table = "`telegram.hype_global_daily`";
        String curDttm = ch.selectMaxDttm(ch.maxDttm(table, "processing_dttm"));
        Date mddtm =  new Date();
        try {
            mddtm = parseDateGMT(curDttm, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            assertThat(Long.valueOf(mddtm.getTime()/1000).doubleValue(), closeTo(unixTime, 5400));
        } catch (AssertionError e) {
            session.sendMessage(channel_test, "Нет данных в таблице " + table + " больше часа!");
        }

        ArrayList<Double> globalHype = ch.selectHype(ch.globalHype("processing_dttm"));
        log.info(String.valueOf(globalHype));
        globalHype.forEach(hype -> {
            try {
                assertThat(hype, is(notNullValue()));
            } catch (AssertionError e) {
                session.sendMessage(channel_test, "В таблице " + table + " есть за поледний час пустые значения для хайпа.");
            }
            try {
                assertThat(hype, is(not(equalTo(0))));
            } catch (AssertionError e) {
                session.sendMessage(channel_test, "В таблице " + table + " есть за поледний час null значения для хайпа.");
            }
        });

    }

}
