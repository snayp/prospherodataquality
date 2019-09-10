package me.datalight;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import me.datalight.autotests.ATManager;
import me.datalight.autotests.TestResult;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseTest {

    protected Logger log = LoggerFactory.getLogger(getClass());
    protected long unixTime = System.currentTimeMillis() / 1000L;

    protected String tableName;
    private String dbManager;

    protected static SlackSession session;
    protected static SlackChannel channel_test;
    protected static SlackChannel channel_data;

    private static ATManager at = new ATManager();
    private TestResult testResult  = new TestResult();
    private TestResult currentTestResult, previousTestResult;
    private int currentTestResultID;

    @BeforeAll
    public static void setupAll() throws IOException {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
        session = Slack.connect();
        channel_test = session.findChannelByName("test");
        channel_data = session.findChannelByName("data_test");
        at.setUrl("jdbc:postgresql://********/autotests");
        at.setUser("postgres");
        at.setPassword("*****");
    }

    @BeforeEach
    public void setupEach() {
        dbManager = getDBManager();
        tableName = getTableName();
    }

    private String getTableName() {
        String className = this.getClass().getName();
        int i = className.indexOf("Test");
        if (this.getClass().getSuperclass().getSimpleName().equals("CHTest")){
            return "`" + className.substring(17,i).toLowerCase() + "`";
        } else {
            return className.substring(23,i).toLowerCase();
        }
    }

    protected final String string  = "нет пустых значений в строковых полях";
    protected final String dates   = "нет пустых значений в поле с датой";
    protected final String numbers = "нет пустых или отрицательных значений в числовых полях";
    protected final String doubles = "нет дубликатов  при группировке по primary keys";
    protected final String today   = "есть данные";
    protected final String diff    = "разница не больше 3%";

    private String getDBManager() {
        if (this.getClass().getSuperclass().getSimpleName().equals("CHTest")){
            return "clh";
        } else {
            return "pg";
        }
    }

    private void setTestResult(String dbManager, String tableName, String check, String params, boolean status) {
        testResult.setDb_manager(dbManager);
        testResult.setSource_name(tableName);
        testResult.setCheck_type(check);
        testResult.setStatus(status);
        testResult.setDate(unixTime);
        testResult.setParams(params);
    }

    private int insertResultsToDB(String params, boolean status){
        setTestResult(dbManager, tableName, Thread.currentThread().getStackTrace()[2].getMethodName(), params, status);
        return at.insertTestResult(testResult);
    }

    private void assertResults(int testID, String reason) {
        currentTestResult = at.selectTestResult(testID);
        previousTestResult = at.findPrevious(currentTestResult);

        if (currentTestResult.isStatus() & !previousTestResult.isStatus()) {
            session.sendMessage(channel_data, "новая ошибка появилась! " +
                currentTestResult.getDb_manager() + ", " +
                currentTestResult.getSource_name() + ", " +
                currentTestResult.getCheck_type() + ", " +
                currentTestResult.getParams() + reason);
        } else if (!currentTestResult.isStatus() & previousTestResult.isStatus()) {
            session.sendMessage(channel_data, "старая ошибка решена! " +
                currentTestResult.getDb_manager() + ", " +
                currentTestResult.getSource_name() + ", " +
                currentTestResult.getCheck_type() + ", " +
                currentTestResult.getParams() + reason);
        }
    }

    protected void assertString(boolean b, String row) {
        try {
            assertThat(tableName + " есть пустые строки в " + row, b, is(false));
            currentTestResultID = insertResultsToDB(row, false);
            assertResults(currentTestResultID, " " + tableName + " больше нет пустых строки в " + row);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB(row, true);
            assertResults(currentTestResultID, " " + tableName + " есть пустые строки в " + row);
        } finally {
            assertThat(tableName + " есть пустые строки в " + row, b, is(false));
        }
    }

    protected void assertNulls(boolean b, String row) {
        try {
            assertThat(tableName + " есть NULL значения в " + row, b, is(false));
            currentTestResultID = insertResultsToDB(row, false);
            assertResults(currentTestResultID, " " + tableName + " больше нет NULL значения в " + row);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB(row, true);
            assertResults(currentTestResultID, " " + tableName + " есть NULL значения в " + row);
        } finally {
            assertThat(tableName + " есть NULL значения в " + row, b, is(false));
        }
    }

    protected void assertNumbersForNagative(boolean b, String row) {
        try {
            assertThat(tableName + " есть значения меньше 0 в " + row, b, is(false));
            currentTestResultID = insertResultsToDB(row, false);
            assertResults(currentTestResultID, " " + tableName + " больше нет значения меньше 0 в " + row);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB(row, true);
            assertResults(currentTestResultID, " " + tableName + " есть значения меньше 0 в " + row);
        } finally {
            assertThat(tableName + " есть значения меньше 0 в " + row, b, is(false));
        }
    }

    protected void assertDoubles(boolean b, String keys) {
        try {
            assertThat(tableName + " есть дубликаты при группировке по " + keys, b, is(false));
            currentTestResultID = insertResultsToDB(keys, false);
            assertResults(currentTestResultID, " " + tableName + " больше нет дубликатов при группировке по " + keys);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB(keys, true);
            assertResults(currentTestResultID, " " + tableName + " есть дубликаты при группировке по " + keys);
        } finally {
            assertThat(tableName + " есть дубликаты при группировке по " + keys, b, is(false));
        }
    }

    protected void assertToday(int count, int greaterThan) {
        try {
            assertThat(tableName + " не все данные загружены за сегодгня", count, greaterThanOrEqualTo(greaterThan));
            currentTestResultID = insertResultsToDB("count = " + count + " greaterThan = " + greaterThan, false);
            assertResults(currentTestResultID, " " + tableName + " все данные загружены за сегодгня count = " + count + " больше чем " + greaterThan);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB("count = " + count + " greaterThan = " + greaterThan, true);
            assertResults(currentTestResultID, " " + tableName + " не все данные загружены за сегодгня count = " + count + " меньше чем " + greaterThan);
        } finally {
            assertThat(tableName + " не все данные загружены за сегодгня", count, greaterThanOrEqualTo(greaterThan));
        }
    }

    protected void assertTodayCoins(int count, String symbol, int greaterThan) {
        try {
            assertThat(tableName + " не все данные загружены за сегодгня", count, greaterThanOrEqualTo(greaterThan));
            currentTestResultID = insertResultsToDB("count для " + symbol + " = " + count + " greaterThan = " + greaterThan, false);
            assertResults(currentTestResultID, " " + tableName + " все данные загружены за сегодгня count для " + symbol + " = " + count + " больше чем " + greaterThan);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB("count для " + symbol + " = " + count + " greaterThan = " + greaterThan, true);
            assertResults(currentTestResultID, " " + tableName + " не все данные загружены за сегодгня count для " + symbol + " = " + count + " меньше чем " + greaterThan);
        } finally {
            assertThat(tableName + " не все данные загружены за сегодгня", count, greaterThanOrEqualTo(greaterThan));
        }
    }

    protected void assertDiff(int today, int yesterday) {
        double diff = Math.abs((today*1.0-yesterday*1.0)/yesterday*1.0);
        try {
            assertThat(tableName + " разница между загруженными данными за вчера и сегодня больше 3%", diff, lessThanOrEqualTo(0.03));
            currentTestResultID = insertResultsToDB("diff = " + diff, false);
            assertResults(currentTestResultID, " " + tableName + " разница между загруженными данными за вчера и сегодня меньше 3% diff = " + diff);
        } catch (AssertionError e) {
            currentTestResultID = insertResultsToDB("diff = " + diff, true);
            assertResults(currentTestResultID, " " + tableName + " разница между загруженными данными за вчера и сегодня больше 3% diff = " + diff);
        } finally {
            assertThat(tableName + " разница между загруженными данными за вчера и сегодня больше 3%", diff, lessThanOrEqualTo(0.03));
        }
    }
}
