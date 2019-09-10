package me.datalight.clh.all;

import com.sun.org.apache.xpath.internal.axes.ChildTestIterator;
import me.datalight.clh.CHTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CoinTest extends CHTest {

    @Test
    void testTg_hype_cap() {

    }

    @Test
    void checkStringsForNullTest() {
        List<String> stringRows = Arrays.asList("id", "symbol");
        stringRows.forEach(stringRow -> {
            boolean b = ch.checkStringsForNull(tableName, stringRow);
            assertString(b, stringRow);
        });
    }
}
