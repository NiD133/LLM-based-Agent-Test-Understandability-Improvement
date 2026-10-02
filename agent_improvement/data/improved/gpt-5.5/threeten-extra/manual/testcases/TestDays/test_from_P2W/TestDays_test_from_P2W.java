package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_P2W {

    private static final int TWO_WEEKS = 2;
    private static final int NO_ADDITIONAL_DAYS = 0;
    private static final int DAYS_IN_TWO_WEEKS = 14;

    @Test
    public void test_from_P2W() {
        assertEquals(
                Days.of(DAYS_IN_TWO_WEEKS),
                Days.from(new MockWeeksDays(TWO_WEEKS, NO_ADDITIONAL_DAYS)));
    }
}
