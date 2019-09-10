package me.datalight;

import io.qameta.allure.Step;
import me.datalight.autotests.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private String url;
    private String user;
    private String password;

    protected int count;
    private String last;
    private String maxDttm;

    protected static Connection conn = null;
    protected static Statement stmt = null;
    protected static ResultSet rs = null;
    protected static boolean b = true;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean isSelectReturnData(String sql) {

        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            log.info("SQL=" + "\n" + "{}", sql);
            b = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return b;
    }

    public int selectCount(String sql) {

        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            log.info("SQL=" + "\n" + "{}", sql);
            b = rs.next();
            count = rs.getInt("count");
            log.info("Было загружено строк = " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return count;
    }

    public String selectLast(String sql) {

        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            log.info("SQL=" + "\n" + "{}", sql);
            b = rs.next();
            last = rs.getString("last");
            log.info("Последнее обновление было " + last);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return last;
    }

    public String selectMaxDttm(String sql) {

        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            log.info("SQL=" + "\n" + "{}", sql);
            b = rs.next();
            maxDttm = rs.getString("maxDttm");
            log.info("Последнее обновление было " + maxDttm);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return maxDttm;
    }

    public ArrayList<Double> selectHype(String sql) {

        ArrayList<Double> hype = new ArrayList<>();
        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            log.info("SQL=" + "\n" + "{}", sql);
            while (rs.next()){
                hype.add(rs.getDouble("hype"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
        return hype;
    }

    public int insertTestResult(TestResult testResult) {
        String SQL = "INSERT INTO data_tests_results(db_manager, source_name, check_type, status, params, \"date\") "
                + "VALUES(?,?,?,?,?,?)";

        int id = 0;

        try {
            conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, testResult.getDb_manager());
            pstmt.setString(2, testResult.getSource_name());
            pstmt.setString(3, testResult.getCheck_type());
            pstmt.setBoolean(4, testResult.isStatus());
            pstmt.setString(5, testResult.getParams());
            pstmt.setLong(6, testResult.getDate());
            pstmt.executeUpdate();
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }

        return id;
    }

    protected void closeConn() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
                log.info("Connection closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step("NULL значения")
    public boolean checkForNull(String tableName, String row) {
        b = isSelectReturnData("" +
                "SELECT *\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + row + " IS NULL\n" +
                "LIMIT 10");
        return b;
    }

    @Step("числа меньше 0")
    public boolean checkNumbersForNegative(String tableName, String number) {
        if (tableName.equals("`coinmarketcap.coin`")) {
            b = isSelectReturnData("" +
                    "SELECT *\n" +
                    "FROM " + tableName + "\n" +
                    "WHERE " + number + " <0\n" +
                    "AND processing_date>today()-1\n" +
                    "LIMIT 10");
            return b;
        } else {
            b = isSelectReturnData("" +
                    "SELECT *\n" +
                    "FROM " + tableName + "\n" +
                    "WHERE " + number + " <0\n" +
                    "LIMIT 10");
            return b;
        }
    }

    @Step("дубли")
    public boolean checkDoubles(String tableName, String primaryKeys, String where) {
        b = isSelectReturnData(selectDoubles(tableName, primaryKeys, where));
        return b;
    }

    @Step("данные за дату")
    public int checkDate(String tableName, String groupBy, String date, String daysBefore) {
        this.count = selectCount(countRows(tableName, groupBy, date, daysBefore));
        return this.count;
    }

    private String selectDoubles(String tableName, String primaryKeys, String where) {
        String selectDoubles = "" +
                "SELECT " + primaryKeys + "\n" +
                "FROM " + tableName + "\n" +
                where +
                "GROUP BY " + primaryKeys + "\n" +
                "HAVING COUNT(*)>1\n"+
                "LIMIT 10";
        return selectDoubles;
    }

    protected String countRows(String tableName, String groupBy, String date, String daysBefore) {
        return "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
