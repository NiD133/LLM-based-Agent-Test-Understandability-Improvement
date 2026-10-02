package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_of_int_singleton {

    private static final int MAX_DAY_OF_MONTH = 31;

    @Test
    public void test_of_int_singleton() {
        for (int dayOfMonth = 1; dayOfMonth <= MAX_DAY_OF_MONTH; dayOfMonth++) {
            DayOfMonth firstLookup = DayOfMonth.of(dayOfMonth);

            assertEquals(dayOfMonth, firstLookup.getValue());
            assertSame(firstLookup, DayOfMonth.of(dayOfMonth));
        }
    }
}
