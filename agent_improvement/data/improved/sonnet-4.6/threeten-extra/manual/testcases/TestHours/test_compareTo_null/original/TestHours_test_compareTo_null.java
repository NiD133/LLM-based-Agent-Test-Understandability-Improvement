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

public class TestHours_test_compareTo_null {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "PT0H", 0 }, { "PT1H", 1 }, { "PT2H", 2 }, { "PT123456789H", 123456789 }, { "PT+0H", 0 }, { "PT+2H", 2 }, { "PT-0H", 0 }, { "PT-2H", -2 }, { "P0D", 0 * 24 }, { "P1D", 1 * 24 }, { "P2D", 2 * 24 }, { "P1234567D", 1234567 * 24 }, { "P+0D", 0 * 24 }, { "P+2D", 2 * 24 }, { "P-0D", 0 * 24 }, { "P-2D", -2 * 24 }, { "P0DT0H", 0 }, { "P1DT2H", 1 * 24 + 2 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3H" }, { "-3H" }, { "P3H" }, { "P3" }, { "P-3" }, { "PH" }, { "T" }, { "T3H" } };
    }

    @Test
    public void test_compareTo_null() {
        Hours test5 = Hours.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }
}
