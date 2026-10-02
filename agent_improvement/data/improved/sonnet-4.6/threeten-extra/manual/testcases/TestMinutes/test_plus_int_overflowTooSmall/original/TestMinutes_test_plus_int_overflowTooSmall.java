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

public class TestMinutes_test_plus_int_overflowTooSmall {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "PT0M", 0 }, { "PT1M", 1 }, { "PT2M", 2 }, { "PT123456789M", 123456789 }, { "PT+0M", 0 }, { "PT+2M", 2 }, { "PT-0M", 0 }, { "PT-2M", -2 }, { "PT0H", 0 }, { "PT1H", 60 }, { "PT2H", 120 }, { "PT1234H", 1234 * 60 }, { "PT+0H", 0 }, { "PT+2H", 120 }, { "PT-0H", 0 }, { "PT-2H", -120 }, { "P0D", 0 }, { "P1D", 1 * 24 * 60 }, { "P2D", 2 * 24 * 60 }, { "P1234D", 1234 * 24 * 60 }, { "P+0D", 0 }, { "P+2D", 2 * 24 * 60 }, { "P-0D", 0 }, { "P-2D", -2 * 24 * 60 }, { "PT0H0M", 0 }, { "PT2H3M", 123 }, { "PT+2H3M", 123 }, { "PT2H+3M", 123 }, { "PT-2H3M", -117 }, { "PT2H-3M", 117 }, { "PT-2H-3M", -123 }, { "P0DT0H0M", 0 }, { "P5DT2H4M", 5 * 24 * 60 + 2 * 60 + 4 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3M" }, { "-3M" }, { "P3M" }, { "P3" }, { "P-3" }, { "PM" }, { "T3" }, { "P3M" }, { "PT3S" }, { "PT3" } };
    }

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).plus(-2));
    }
}
