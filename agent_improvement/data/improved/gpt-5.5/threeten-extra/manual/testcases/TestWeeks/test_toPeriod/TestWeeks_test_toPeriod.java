package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_toPeriod {

    private static final int FIRST_WEEK_COUNT = -20;
    private static final int AFTER_LAST_WEEK_COUNT = 20;

    @Test
    public void test_toPeriod() {
        for (int weekCount = FIRST_WEEK_COUNT; weekCount < AFTER_LAST_WEEK_COUNT; weekCount++) {
            assertEquals(Period.ofWeeks(weekCount), Weeks.of(weekCount).toPeriod());
        }
    }
}
