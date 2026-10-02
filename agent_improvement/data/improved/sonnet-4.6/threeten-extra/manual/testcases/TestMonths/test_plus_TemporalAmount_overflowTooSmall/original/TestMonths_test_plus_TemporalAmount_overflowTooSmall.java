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

public class TestMonths_test_plus_TemporalAmount_overflowTooSmall {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "P0M", 0 }, { "P1M", 1 }, { "P2M", 2 }, { "P123456789M", 123456789 }, { "P+0M", 0 }, { "P+2M", 2 }, { "P-0M", 0 }, { "P-2M", -2 }, { "P0Y", 0 }, { "P1Y", 12 }, { "P2Y", 24 }, { "P1234567Y", 1234567 * 12 }, { "P+0Y", 0 }, { "P+2Y", 24 }, { "P-0Y", 0 }, { "P-2Y", -24 }, { "P0Y0M", 0 }, { "P2Y3M", 27 }, { "P+2Y3M", 27 }, { "P2Y+3M", 27 }, { "P-2Y3M", -21 }, { "P2Y-3M", 21 }, { "P-2Y-3M", -27 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3D" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3M" }, { "-3M" }, { "P3" }, { "P-3" }, { "PM" } };
    }

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE + 1).plus(Months.of(-2)));
    }
}
