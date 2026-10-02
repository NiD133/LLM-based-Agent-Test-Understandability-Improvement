package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // St. Tib's Day (month=0, day=0) is its own pseudo-month containing exactly 1 day
        assertEquals(1, DiscordianDate.of(3178, 0, 0).lengthOfMonth());

        // A regular Discordian month always contains 73 days, verified at the first day
        assertEquals(73, DiscordianDate.of(3178, 1, 1).lengthOfMonth());

        // A regular Discordian month always contains 73 days, verified at the last day
        assertEquals(73, DiscordianDate.of(3178, 1, 73).lengthOfMonth());
    }
}
