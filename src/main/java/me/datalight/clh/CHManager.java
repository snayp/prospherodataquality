package me.datalight.clh;

import io.qameta.allure.Step;
import me.datalight.DBManager;

public class CHManager extends DBManager {

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

    @Step("пустые строки")
    public boolean checkStringsForNull(String tableName, String stringRow) {
        b = isSelectReturnData("" +
            "SELECT *\n" +
            "FROM " + tableName + "\n" +
            "WHERE empty(" + stringRow + ")\n" +
            "LIMIT 10");
        return b;
    }

    protected String countRows(String tableName, String groupBy, String date, String daysBefore) {
        return "" +
            "SELECT COUNT(*) AS count\n" +
            "FROM\n" +
            "(SELECT " + groupBy + "\n" +
            "FROM " + tableName + "\n" +
            "WHERE " + date + "=today()" + daysBefore + "\n" +
            "GROUP BY " + groupBy + ")";
    }

    String lastUpdate(String tableName, String date) {
        return "" +
            "SELECT MAX(" + date + ") AS last\n" +
            "FROM " + tableName;
    }

    String maxDttm(String tableName, String date) {
        return  "SELECT max("+date+") as maxDttm FROM " + tableName;
    }

    String maxDttmWhere(String tableName, String date, String where) {
        return  "SELECT max("+date+") as maxDttm FROM " + tableName + where;
    }

    String lastUpdateWhere(String tableName, String date, String where) {
        return "" +
            "SELECT MAX(" + date + ") AS last\n" +
            "FROM " + tableName + "\n" +
            where;
    }

    String globalHype(String date) {
        return "SELECT hype FROM `telegram.hype_global_daily`\n" +
                "WHERE processing_dttm = (" + maxDttm("`telegram.hype_global_daily`", "processing_dttm") + ")";
    }



}
