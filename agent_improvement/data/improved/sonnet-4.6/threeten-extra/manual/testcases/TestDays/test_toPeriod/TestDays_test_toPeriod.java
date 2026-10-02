package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_toPeriod {

    // Range covers negative values, zero, and positive values
    private static final int RANGE_START = -20;
    private static final int RANGE_END   =  20; // exclusive

    @Test
    public void test_toPeriod() {
        for (int dayCount = RANGE_START; dayCount < RANGE_END; dayCount++) {
            assertEquals(Period.ofDays(dayCount), Days.of(dayCount).toPeriod());
        }
    }
}
