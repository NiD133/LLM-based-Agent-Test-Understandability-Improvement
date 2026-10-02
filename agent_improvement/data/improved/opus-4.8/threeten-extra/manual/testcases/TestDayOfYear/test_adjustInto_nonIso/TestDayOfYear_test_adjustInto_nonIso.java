package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear} covering {@code now(...)} and the non-ISO
 * behaviour of {@code adjustInto(Temporal)}.
 */
public class TestDayOfYear_test_adjustInto_nonIso {

    /** An arbitrary day-of-year used as the adjuster under test. */
    private static final DayOfYear TEST = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfYear.now() must agree with the day-of-year of the current local date.
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");

        // DayOfYear.now(zone) must agree with the current local date in that zone.
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // adjustInto(Temporal) on a non-ISO calendar
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_nonIso() {
        // Adjusting a non-ISO temporal (Japanese calendar) is not supported.
        assertThrows(DateTimeException.class, () -> TEST.adjustInto(JapaneseDate.now()));
    }
}
