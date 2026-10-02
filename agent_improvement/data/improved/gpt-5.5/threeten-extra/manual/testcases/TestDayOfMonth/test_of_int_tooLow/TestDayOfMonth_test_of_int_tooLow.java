package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_of_int_tooLow {

    @Test
    public void test_of_int_tooLow() {
        int dayBelowValidRange = 0;

        assertThrows(DateTimeException.class, () -> DayOfMonth.of(dayBelowValidRange));
    }
}
