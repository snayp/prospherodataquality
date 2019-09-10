package me.datalight.clh;

import io.qameta.allure.Epic;
import me.datalight.BaseTest;
import org.junit.jupiter.api.BeforeAll;

@Epic("Проверка clickhouse")
public class CHTest extends BaseTest {

    public static CHManager ch = new CHManager();

    @BeforeAll
    public static void setupDB() {
        ch.setUrl("jdbc:clickhouse://*******/default");
        ch.setUser("default");
        ch.setPassword("***********");
    }
    //clh-new.datalight.me
    //clh.datalight.me
}
