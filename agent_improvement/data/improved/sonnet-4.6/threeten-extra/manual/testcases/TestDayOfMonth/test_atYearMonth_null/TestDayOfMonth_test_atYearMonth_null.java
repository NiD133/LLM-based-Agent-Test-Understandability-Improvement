package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_atYearMonth_null {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    @Test
    public void test_atYearMonth_null() {
        assertThrows(NullPointerException.class, () -> TEST.atYearMonth((YearMonth) null));
    }
}
