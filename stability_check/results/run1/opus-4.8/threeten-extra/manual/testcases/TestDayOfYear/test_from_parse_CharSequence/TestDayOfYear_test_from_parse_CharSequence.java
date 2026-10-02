package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for the factory methods that build a {@link DayOfYear}:
 * {@code now()}, {@code now(ZoneId)} and parsing via {@code DayOfYear::from}.
 */
public class TestDayOfYear_test_from_parse_CharSequence {

    /**
     * {@code DayOfYear.now()} must report the same day-of-year as
     * {@code LocalDate.now()} in the default time-zone.
     * Retried because both calls could straddle a midnight boundary.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code DayOfYear.now(zone)} must report the same day-of-year as
     * {@code LocalDate.now(zone)} for the same time-zone.
     * Retried because both calls could straddle a midnight boundary.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * Parsing a day-of-year string with the "D" pattern and the
     * {@code DayOfYear::from} query must yield the matching {@code DayOfYear}.
     */
    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");

        DayOfYear parsed = dayOfYearFormatter.parse("76", DayOfYear::from);

        assertEquals(DayOfYear.of(76), parsed);
    }
}
