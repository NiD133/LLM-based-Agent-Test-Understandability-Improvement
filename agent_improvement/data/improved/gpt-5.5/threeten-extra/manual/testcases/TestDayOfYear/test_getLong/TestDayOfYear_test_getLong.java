package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_getLong {

    private static final DayOfYear TEST_DAY = DayOfYear.of(12);

    @Test
    public void test_getLong() {
        assertEquals(12L, TEST_DAY.getLong(DAY_OF_YEAR));
    }
}
