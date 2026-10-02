package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

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

    // "D" is the ISO day-of-year pattern; parsing "76" must produce DayOfYear 76
    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter dayOfYearFormatter = DateTimeFormatter.ofPattern("D");
        DayOfYear parsed = dayOfYearFormatter.parse("76", DayOfYear::from);
        assertEquals(DayOfYear.of(76), parsed);
    }
}
