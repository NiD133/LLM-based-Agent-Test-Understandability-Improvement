package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_atMonth_tooLow {

    private static final DayOfMonth TEST_DAY = DayOfMonth.of(12);
    private static final int INVALID_MONTH_BELOW_RANGE = 0;

    @Test
    public void test_atMonth_tooLow() {
        assertThrows(DateTimeException.class, () -> TEST_DAY.atMonth(INVALID_MONTH_BELOW_RANGE));
    }
}
