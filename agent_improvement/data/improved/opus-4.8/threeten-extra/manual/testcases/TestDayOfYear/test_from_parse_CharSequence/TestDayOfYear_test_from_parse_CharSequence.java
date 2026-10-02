package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory and "now" methods of {@link DayOfYear}.
 */
public class TestDayOfYear_test_from_parse_CharSequence {

    /**
     * {@code DayOfYear.now()} should report the same day-of-year as the current local date.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code DayOfYear.now(zone)} should report the same day-of-year as the current date in that zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * Parsing a day-of-year token with {@code DayOfYear::from} should yield the matching {@code DayOfYear}.
     */
    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");

        DayOfYear parsed = dayOfYearFormatter.parse("76", DayOfYear::from);

        assertEquals(DayOfYear.of(76), parsed);
    }
}
