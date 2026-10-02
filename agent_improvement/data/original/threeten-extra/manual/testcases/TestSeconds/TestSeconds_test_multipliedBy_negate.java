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

public class TestSeconds_test_multipliedBy_negate {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "PT0S", 0 }, { "PT1S", 1 }, { "PT2S", 2 }, { "PT123456789S", 123456789 }, { "PT+0S", 0 }, { "PT+2S", 2 }, { "PT-0S", 0 }, { "PT-2S", -2 }, { "PT0M", 0 }, { "PT1M", 60 }, { "PT2M", 120 }, { "PT1234M", 1234 * 60 }, { "PT+0M", 0 }, { "PT+2M", 120 }, { "PT-0M", 0 }, { "PT-2M", -120 }, { "PT0H", 0 }, { "PT1H", 60 * 60 }, { "PT2H", 120 * 60 }, { "PT1234H", 1234 * 60 * 60 }, { "PT+0H", 0 }, { "PT+2H", 120 * 60 }, { "PT-0H", 0 }, { "PT-2H", -120 * 60 }, { "P0D", 0 }, { "P1D", 60 * 60 * 24 }, { "P2D", 120 * 60 * 24 }, { "P1234D", 1234 * 60 * 60 * 24 }, { "P+0D", 0 }, { "P+2D", 120 * 60 * 24 }, { "P-0D", 0 }, { "P-2D", -120 * 60 * 24 }, { "PT0M0S", 0 }, { "PT2M3S", 2 * 60 + 3 }, { "PT+2M3S", 2 * 60 + 3 }, { "PT2M+3S", 2 * 60 + 3 }, { "PT-2M3S", -2 * 60 + 3 }, { "PT2M-3S", 2 * 60 - 3 }, { "PT-2M-3S", -2 * 60 - 3 }, { "PT0H0S", 0 }, { "PT2H3S", 2 * 3600 + 3 }, { "PT+2H3S", 2 * 3600 + 3 }, { "PT2H+3S", 2 * 3600 + 3 }, { "PT-2H3S", -2 * 3600 + 3 }, { "PT2H-3S", 2 * 3600 - 3 }, { "PT-2H-3S", -2 * 3600 - 3 }, { "P0DT0H0M0S", 0 }, { "P5DT2H4M3S", 5 * 86400 + 2 * 3600 + 4 * 60 + 3 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3S" }, { "-3S" }, { "P3S" }, { "P3" }, { "P-3" }, { "PS" }, { "T3" }, { "PT3" } };
    }

    @Test
    public void test_multipliedBy_negate() {
        Seconds test5 = Seconds.of(5);
        assertEquals(Seconds.of(-15), test5.multipliedBy(-3));
    }
}
