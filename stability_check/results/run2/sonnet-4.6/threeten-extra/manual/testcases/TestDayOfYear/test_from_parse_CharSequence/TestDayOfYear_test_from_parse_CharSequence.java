package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_parse_CharSequence {

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // Verifies that DayOfYear.from() can be used as a TemporalQuery to parse
    // a day-of-year value from a formatted string using the "D" pattern.
    @Test
    public void test_from_parse_CharSequence() {
        // "D" is the DateTimeFormatter pattern for day-of-year
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");

        DayOfYear parsed = dayOfYearFormatter.parse("76", DayOfYear::from);

        assertEquals(DayOfYear.of(76), parsed);
    }
}
