package me.datalight.prosphero;

import io.qameta.allure.Epic;
import me.datalight.BaseTest;
import org.junit.jupiter.api.BeforeAll;

@Epic("Проверка postgresql")
public class PGTest extends BaseTest {

    protected static PGManager pg = new PGManager();

    @BeforeAll
    public static void setupDB() {
        pg.setUrl("jdbc:postgresql://********/prosphero");
        pg.setUser("postgres");
        pg.setPassword("*******");
    }
}
