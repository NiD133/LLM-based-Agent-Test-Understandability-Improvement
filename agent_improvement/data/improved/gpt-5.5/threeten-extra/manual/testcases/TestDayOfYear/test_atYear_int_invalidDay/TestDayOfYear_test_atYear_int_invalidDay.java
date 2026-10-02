package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_int_invalidDay {

    private static final DayOfYear DAY_TWELVE = DayOfYear.of(12);

    @RetryingTest(100)
    public void test_now() {
        assertEquals(
                LocalDate.now().getDayOfYear(),
                DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        assertEquals(
                LocalDate.now(tokyo).getDayOfYear(),
                DayOfYear.now(tokyo).getValue());
    }

    @Test
    public void test_atYear_int_invalidDay() {
        int yearBeforeSupportedRange = Year.MIN_VALUE - 1;

        assertThrows(DateTimeException.class, () -> DAY_TWELVE.atYear(yearBeforeSupportedRange));
    }
}
