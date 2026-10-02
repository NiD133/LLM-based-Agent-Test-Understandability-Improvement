package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Year;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_int_invalidDay {

    // An arbitrary valid day-of-year used as the receiver for atYear(int)
    private static final DayOfYear TEST = DayOfYear.of(12);

    @Test
    public void test_atYear_int_invalidDay() {
        // Year.MIN_VALUE - 1 is outside the valid year range, so atYear must reject it
        assertThrows(DateTimeException.class, () -> TEST.atYear(Year.MIN_VALUE - 1));
    }
}
