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

public class TestWeeks_test_dividedBy {

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3Y" }, { "P3M" }, { "P3D" }, { "3" }, { "-3" }, { "3Y" }, { "-3Y" }, { "P3" }, { "P-3" }, { "PY" } };
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_dividedBy() {
        Weeks test12 = Weeks.of(12);
        assertEquals(Weeks.of(12), test12.dividedBy(1));
        assertEquals(Weeks.of(6), test12.dividedBy(2));
        assertEquals(Weeks.of(4), test12.dividedBy(3));
        assertEquals(Weeks.of(3), test12.dividedBy(4));
        assertEquals(Weeks.of(2), test12.dividedBy(5));
        assertEquals(Weeks.of(2), test12.dividedBy(6));
        assertEquals(Weeks.of(-4), test12.dividedBy(-3));
    }
}
