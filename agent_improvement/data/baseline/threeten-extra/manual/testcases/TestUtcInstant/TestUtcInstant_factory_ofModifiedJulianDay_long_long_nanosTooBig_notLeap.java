package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.testing.EqualsTester;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap {

    private static final long MJD_1972_12_30 = 41681;

    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long MJD_1973_01_01 = 41683;

    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1000000000L;

    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    public static Object[][] data_badParse() {
        return new Object[][] { { "" }, { "A" }, // bad month
        { "2012-13-01T00:00:00Z" } };
    }

    //-----------------------------------------------------------------------
    @Nullable
    public static Object[][] data_withModifiedJulianDay() {
        return new @Nullable Object[][] { { 0L, 12345L, 1L, 1L, 12345L }, { 0L, 12345L, -1L, -1L, 12345L }, { 7L, 12345L, 2L, 2L, 12345L }, { 7L, 12345L, -2L, -2L, 12345L }, { -99L, 12345L, 3L, 3L, 12345L }, { -99L, 12345L, -3L, -3L, 12345L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY } };
    }

    //-----------------------------------------------------------------------
    @Nullable
    public static Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] { { 0L, 12345L, 1L, 0L, 1L }, { 0L, 12345L, -1L, null, 0L }, { 7L, 12345L, 2L, 7L, 2L }, { -99L, 12345L, 3L, -99L, 3L }, { MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30, NANOS_PER_DAY - 1 }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1 }, { MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01, NANOS_PER_DAY - 1 }, { MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY, MJD_1972_12_31_LEAP, NANOS_PER_DAY }, { MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L }, { MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1 }, { MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L }, { MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L }, { MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] { { 0, 0, -2 * SECS_PER_DAY, 5, -2, 5 }, { 0, 0, -1 * SECS_PER_DAY, 1, -1, 1 }, { 0, 0, -1 * SECS_PER_DAY, 0, -1, 0 }, { 0, 0, 0, -2, -1, NANOS_PER_DAY - 2 }, { 0, 0, 0, -1, -1, NANOS_PER_DAY - 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 1 }, { 0, 0, 0, 2, 0, 2 }, { 0, 0, 1, 0, 0, 1 * NANOS_PER_SEC }, { 0, 0, 2, 0, 0, 2 * NANOS_PER_SEC }, { 0, 0, 3, 333333333, 0, 3 * NANOS_PER_SEC + 333333333 }, { 0, 0, 1 * SECS_PER_DAY, 0, 1, 0 }, { 0, 0, 1 * SECS_PER_DAY, 1, 1, 1 }, { 0, 0, 2 * SECS_PER_DAY, 5, 2, 5 }, { 1, 0, -2 * SECS_PER_DAY, 5, -1, 5 }, { 1, 0, -1 * SECS_PER_DAY, 1, 0, 1 }, { 1, 0, -1 * SECS_PER_DAY, 0, 0, 0 }, { 1, 0, 0, -2, 0, NANOS_PER_DAY - 2 }, { 1, 0, 0, -1, 0, NANOS_PER_DAY - 1 }, { 1, 0, 0, 0, 1, 0 }, { 1, 0, 0, 1, 1, 1 }, { 1, 0, 0, 2, 1, 2 }, { 1, 0, 1, 0, 1, 1 * NANOS_PER_SEC }, { 1, 0, 2, 0, 1, 2 * NANOS_PER_SEC }, { 1, 0, 3, 333333333, 1, 3 * NANOS_PER_SEC + 333333333 }, { 1, 0, 1 * SECS_PER_DAY, 0, 2, 0 }, { 1, 0, 1 * SECS_PER_DAY, 1, 2, 1 }, { 1, 0, 2 * SECS_PER_DAY, 5, 3, 5 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_minus() {
        return new Object[][] { { 0, 0, 2 * SECS_PER_DAY, -5, -2, 5 }, { 0, 0, 1 * SECS_PER_DAY, -1, -1, 1 }, { 0, 0, 1 * SECS_PER_DAY, 0, -1, 0 }, { 0, 0, 0, 2, -1, NANOS_PER_DAY - 2 }, { 0, 0, 0, 1, -1, NANOS_PER_DAY - 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, -1, 0, 1 }, { 0, 0, 0, -2, 0, 2 }, { 0, 0, -1, 0, 0, 1 * NANOS_PER_SEC }, { 0, 0, -2, 0, 0, 2 * NANOS_PER_SEC }, { 0, 0, -3, -333333333, 0, 3 * NANOS_PER_SEC + 333333333 }, { 0, 0, -1 * SECS_PER_DAY, 0, 1, 0 }, { 0, 0, -1 * SECS_PER_DAY, -1, 1, 1 }, { 0, 0, -2 * SECS_PER_DAY, -5, 2, 5 }, { 1, 0, 2 * SECS_PER_DAY, -5, -1, 5 }, { 1, 0, 1 * SECS_PER_DAY, -1, 0, 1 }, { 1, 0, 1 * SECS_PER_DAY, 0, 0, 0 }, { 1, 0, 0, 2, 0, NANOS_PER_DAY - 2 }, { 1, 0, 0, 1, 0, NANOS_PER_DAY - 1 }, { 1, 0, 0, 0, 1, 0 }, { 1, 0, 0, -1, 1, 1 }, { 1, 0, 0, -2, 1, 2 }, { 1, 0, -1, 0, 1, 1 * NANOS_PER_SEC }, { 1, 0, -2, 0, 1, 2 * NANOS_PER_SEC }, { 1, 0, -3, -333333333, 1, 3 * NANOS_PER_SEC + 333333333 }, { 1, 0, -1 * SECS_PER_DAY, 0, 2, 0 }, { 1, 0, -1 * SECS_PER_DAY, -1, 2, 1 }, { 1, 0, -2 * SECS_PER_DAY, -5, 3, 5 } };
    }

    void doTest_comparisons_UtcInstant(UtcInstant... instants) {
        for (int i = 0; i < instants.length; i++) {
            UtcInstant a = instants[i];
            for (int j = 0; j < instants.length; j++) {
                UtcInstant b = instants[j];
                if (i < j) {
                    assertEquals(-1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertTrue(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                } else if (i > j) {
                    assertEquals(1, a.compareTo(b));
                    assertNotEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertTrue(a.isAfter(b));
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertEquals(a, b);
                    assertFalse(a.isBefore(b));
                    assertFalse(a.isAfter(b));
                }
            }
        }
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_toString() {
        return new Object[][] { { 40587, 0, "1970-01-01T00:00:00Z" }, { 40588, 1, "1970-01-02T00:00:00.000000001Z" }, { 40588, 999, "1970-01-02T00:00:00.000000999Z" }, { 40588, 1000, "1970-01-02T00:00:00.000001Z" }, { 40588, 999000, "1970-01-02T00:00:00.000999Z" }, { 40588, 1000000, "1970-01-02T00:00:00.001Z" }, { 40618, 999999999, "1970-02-01T00:00:00.999999999Z" }, { 40619, 1000000000, "1970-02-02T00:00:01Z" }, { 40620, 60L * 1000000000L, "1970-02-03T00:01:00Z" }, { 40621, 60L * 60L * 1000000000L, "1970-02-04T01:00:00Z" }, { MJD_1972_12_31_LEAP, 24L * 60L * 60L * 1000000000L - 1000000000L, "1972-12-31T23:59:59Z" }, { MJD_1972_12_31_LEAP, NANOS_PER_DAY, "1972-12-31T23:59:60Z" }, { MJD_1973_01_01, 0, "1973-01-01T00:00:00Z" } };
    }

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }
}
