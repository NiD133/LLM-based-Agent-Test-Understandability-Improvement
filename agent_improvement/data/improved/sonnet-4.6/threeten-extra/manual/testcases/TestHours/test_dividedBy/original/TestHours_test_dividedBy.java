package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.testing.EqualsTester;

public class TestHours_test_dividedBy {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "PT0H", 0 }, { "PT1H", 1 }, { "PT2H", 2 }, { "PT123456789H", 123456789 }, { "PT+0H", 0 }, { "PT+2H", 2 }, { "PT-0H", 0 }, { "PT-2H", -2 }, { "P0D", 0 * 24 }, { "P1D", 1 * 24 }, { "P2D", 2 * 24 }, { "P1234567D", 1234567 * 24 }, { "P+0D", 0 * 24 }, { "P+2D", 2 * 24 }, { "P-0D", 0 * 24 }, { "P-2D", -2 * 24 }, { "P0DT0H", 0 }, { "P1DT2H", 1 * 24 + 2 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3H" }, { "-3H" }, { "P3H" }, { "P3" }, { "P-3" }, { "PH" }, { "T" }, { "T3H" } };
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_dividedBy() {
        Hours test12 = Hours.of(12);
        assertEquals(Hours.of(12), test12.dividedBy(1));
        assertEquals(Hours.of(6), test12.dividedBy(2));
        assertEquals(Hours.of(4), test12.dividedBy(3));
        assertEquals(Hours.of(3), test12.dividedBy(4));
        assertEquals(Hours.of(2), test12.dividedBy(5));
        assertEquals(Hours.of(2), test12.dividedBy(6));
        assertEquals(Hours.of(-4), test12.dividedBy(-3));
    }
}
