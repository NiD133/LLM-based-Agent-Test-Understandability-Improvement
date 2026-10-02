package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_get_invalidField {

    private static final Year YEAR_STANDARD = Year.of(2007);
    private static final Year YEAR_LEAP = Year.of(2008);
    private static final int STANDARD_YEAR_LENGTH = 365;
    private static final int LEAP_YEAR_LENGTH = 366;
    private static final DayOfYear TEST = DayOfYear.of(12);
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_get_invalidField() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> TEST.get(MONTH_OF_YEAR));
    }
}
