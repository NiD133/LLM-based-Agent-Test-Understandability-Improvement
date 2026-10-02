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
 * Tests for {@link DayOfMonth#adjustInto(java.time.temporal.Temporal)}.
 * The method only supports ISO chronology dates; non-ISO dates (e.g. Japanese) must throw.
 */
public class TestDayOfMonth_test_adjustInto_nonIso {

    // Day-of-month used as the adjuster under test
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // adjustInto rejects non-ISO chronologies (e.g. Japanese calendar)
    @Test
    public void test_adjustInto_nonIso() {
        assertThrows(DateTimeException.class, () -> TEST.adjustInto(JapaneseDate.now()));
    }
}
