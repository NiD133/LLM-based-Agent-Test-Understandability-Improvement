package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_range {

    private static final DayOfMonth DAY_TWELVE = DayOfMonth.of(12);

    @Test
    public void test_range() {
        assertEquals(DAY_OF_MONTH.range(), DAY_TWELVE.range(DAY_OF_MONTH));
    }
}
