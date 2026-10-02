package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_lengthOfMonth_specific {

    private static final int LEAP_YEAR_WITH_ST_TIBS_DAY = 3178;
    private static final int ST_TIBS_MONTH = 0;
    private static final int ST_TIBS_DAY = 0;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int LAST_DAY_OF_MONTH = 73;

    @Test
    public void test_lengthOfMonth_specific() {
        assertEquals(1, DiscordianDate.of(LEAP_YEAR_WITH_ST_TIBS_DAY, ST_TIBS_MONTH, ST_TIBS_DAY).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(LEAP_YEAR_WITH_ST_TIBS_DAY, FIRST_MONTH, FIRST_DAY_OF_MONTH).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(LEAP_YEAR_WITH_ST_TIBS_DAY, FIRST_MONTH, LAST_DAY_OF_MONTH).lengthOfMonth());
    }
}
