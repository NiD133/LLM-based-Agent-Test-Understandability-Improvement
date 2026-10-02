package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear#from(java.time.temporal.TemporalAccessor)} via
 * {@link DateTimeFormatter#parse(CharSequence, java.time.temporal.TemporalQuery)}.
 */
public class TestDayOfYear_test_from_parse_CharSequence {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    /**
     * Verifies that a {@code DayOfYear} can be obtained by parsing a day-of-year
     * string with the "D" pattern and using {@code DayOfYear::from} as the query.
     * Input "76" should produce {@code DayOfYear.of(76)}.
     */
    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");
        DayOfYear expected = DayOfYear.of(76);

        DayOfYear actual = dayOfYearFormatter.parse("76", DayOfYear::from);

        assertEquals(expected, actual);
    }
}
