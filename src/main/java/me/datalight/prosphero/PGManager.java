package me.datalight.prosphero;

import io.qameta.allure.Step;
import me.datalight.DBManager;

public class PGManager extends DBManager {

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
                "WHERE trim(" + stringRow + ") = ''\n" +
                "LIMIT 10");
        return b;
    }

    protected String countRows(String tableName, String groupBy, String date, String daysBefore) {
        String countRows = "" +
                "SELECT COUNT(*) AS count\n" +
                "FROM\n" +
                "(SELECT " + groupBy + "\n" +
                "FROM " + tableName + "\n" +
                "WHERE date_trunc('day', " + date + ")=current_date" + daysBefore + "\n" +
                "GROUP BY " + groupBy + ") s";
        return countRows;
    }
}