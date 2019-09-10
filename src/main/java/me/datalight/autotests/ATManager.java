package me.datalight.autotests;

import me.datalight.DBManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class ATManager extends DBManager {

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
    }

    @Override
    public void setUser(String user) {
        super.setUser(user);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    private TestResult testResult;
    private QueryRunner run = new QueryRunner();
    private ResultSetHandler<TestResult> resultHandler = new BeanHandler<>(TestResult.class);

    public TestResult selectTestResult(int id) {
        try {
            conn = connect();
            testResult = run.query(conn, "SELECT * FROM data_tests_results WHERE id=?", resultHandler, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return testResult;
    }

    public TestResult findPrevious(TestResult result) {
        String sql;
        if (result.getCheck_type().equals("assertToday") | result.getCheck_type().equals("assertTodayCoins") | result.getCheck_type().equals("assertDiff")) {
            sql = "" +
                    "SELECT *\n" +
                    "FROM data_tests_results\n" +
                    "WHERE db_manager = '" + result.getDb_manager() + "'\n" +
                    "AND source_name = '" + result.getSource_name() + "'\n" +
                    "AND check_type = '" + result.getCheck_type() + "'\n" +
                    "AND id < " + result.getId() + "\n" +
                    "ORDER BY date desc\n" +
                    "LIMIT 1;";
        } else {
            sql = "" +
                    "SELECT *\n" +
                    "FROM data_tests_results\n" +
                    "WHERE db_manager = '" + result.getDb_manager() + "'\n" +
                    "AND source_name = '" + result.getSource_name() + "'\n" +
                    "AND check_type = '" + result.getCheck_type() + "'\n" +
                    "AND params = '" + result.getParams() + "'\n" +
                    "AND id < " + result.getId() + "\n" +
                    "ORDER BY date desc\n" +
                    "LIMIT 1;";
        }

        try {
            conn = connect();
            testResult = run.query(conn, sql, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return testResult;
    }
}
