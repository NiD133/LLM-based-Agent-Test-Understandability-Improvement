package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_get {

    private static final DayOfYear DAY_TWELVE = DayOfYear.of(12);
    private static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        assertEquals(LocalDate.now(TOKYO).getDayOfYear(), DayOfYear.now(TOKYO).getValue());
    }

    @Test
    public void test_get() {
        assertEquals(12, DAY_TWELVE.get(DAY_OF_YEAR));
    }
}
