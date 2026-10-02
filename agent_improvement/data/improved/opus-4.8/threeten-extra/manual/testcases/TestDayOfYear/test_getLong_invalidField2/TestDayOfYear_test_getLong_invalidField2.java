package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code getLong(TemporalField)} with an unsupported field.
 */
public class TestDayOfYear_test_getLong_invalidField2 {

    /** An arbitrary, fixed day-of-year used as the subject under test. */
    private static final DayOfYear TEST = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // The current day-of-year must match LocalDate's, in the default time-zone.
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        // The current day-of-year must match LocalDate's, in a specific time-zone.
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_getLong_invalidField2() {
        // DAY_OF_QUARTER is not supported by DayOfYear, so getLong must reject it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.getLong(IsoFields.DAY_OF_QUARTER));
    }
}
