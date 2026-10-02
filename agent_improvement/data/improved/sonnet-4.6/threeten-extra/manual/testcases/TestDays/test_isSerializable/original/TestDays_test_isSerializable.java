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
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.testing.EqualsTester;

public class TestDays_test_isSerializable {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "P0D", 0 }, { "P1D", 1 }, { "P2D", 2 }, { "P123456789D", 123456789 }, { "P+0D", 0 }, { "P+2D", 2 }, { "P-0D", 0 }, { "P-2D", -2 }, { "P0W", 0 }, { "P1W", 7 }, { "P2W", 14 }, { "P12345678W", 12345678 * 7 }, { "P+0W", 0 }, { "P+2W", 14 }, { "P-0W", 0 }, { "P-2W", -14 }, { "P0W0D", 0 }, { "P2W3D", 17 }, { "P+2W3D", 17 }, { "P2W+3D", 17 }, { "P-2W3D", -11 }, { "P2W-3D", 11 }, { "P-2W-3D", -17 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3Y" }, { "P3M" }, { "P3Q" }, { "P1D2W" }, { "3" }, { "-3" }, { "3D" }, { "-3D" }, { "P3" }, { "P-3" }, { "P" }, { "PD" }, { "PW" } };
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_isSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(Days.class));
    }
}
