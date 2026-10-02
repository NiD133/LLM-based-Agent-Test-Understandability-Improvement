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

public class TestYears_test_minus_int {

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3M" }, { "P3W" }, { "P3D" }, { "3" }, { "-3" }, { "3Y" }, { "-3Y" }, { "P3" }, { "P-3" }, { "PY" } };
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_minus_int() {
        Years test5 = Years.of(5);
        assertEquals(Years.of(5), test5.minus(0));
        assertEquals(Years.of(3), test5.minus(2));
        assertEquals(Years.of(7), test5.minus(-2));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
