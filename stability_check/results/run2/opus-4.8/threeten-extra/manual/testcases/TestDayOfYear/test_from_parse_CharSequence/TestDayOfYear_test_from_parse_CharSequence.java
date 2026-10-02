package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods of {@link DayOfYear} that read the current day
 * ({@code now}) and that build an instance from parsed text ({@code from}).
 */
public class TestDayOfYear_test_from_parse_CharSequence {

    /**
     * {@code DayOfYear.now()} should report the same day-of-year as the current
     * local date in the system default time-zone.
     * Retried because the clock can roll to a new day between the two reads.
     */
    @RetryingTest(100)
    public void now_matchesCurrentLocalDate() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code DayOfYear.now(zone)} should report the same day-of-year as the
     * current local date in that same zone.
     * Retried because the clock can roll to a new day between the two reads.
     */
    @RetryingTest(100)
    public void now_withZone_matchesCurrentLocalDateInZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * Parsing the day-of-year pattern "D" and querying with {@code DayOfYear::from}
     * should yield the matching {@code DayOfYear} instance.
     */
    @Test
    public void from_parseCharSequence_returnsMatchingDayOfYear() {
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");
        DayOfYear parsed = dayOfYearFormatter.parse("76", DayOfYear::from);
        assertEquals(DayOfYear.of(76), parsed);
    }
}
