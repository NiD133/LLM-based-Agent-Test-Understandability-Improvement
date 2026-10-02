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
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        int actualDayOfYear = DayOfYear.now().getValue();

        assertEquals(expectedDayOfYear, actualDayOfYear);
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(zone).getDayOfYear();
        int actualDayOfYear = DayOfYear.now(zone).getValue();

        assertEquals(expectedDayOfYear, actualDayOfYear);
    }

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("D");
        DayOfYear parsedDayOfYear = formatter.parse("76", DayOfYear::from);

        assertEquals(DayOfYear.of(76), parsedDayOfYear);
    }
}
