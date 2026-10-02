package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code getLong} with an unsupported field.
 */
public class TestDayOfYear_test_getLong_invalidField {

    /** A fixed sample day-of-year used by the field-query test. */
    private static final DayOfYear DAY_12 = DayOfYear.of(12);

    @RetryingTest(100)
    public void now_matchesCurrentDayOfYearInDefaultZone() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void now_matchesCurrentDayOfYearInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    @Test
    public void getLong_withUnsupportedField_throws() {
        // DayOfYear only supports DAY_OF_YEAR; any other ChronoField is rejected.
        assertThrows(UnsupportedTemporalTypeException.class, () -> DAY_12.getLong(MONTH_OF_YEAR));
    }
}
