package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting 0 years, 2 months, and 3 days from YOLD 2014-5-26
        // should land on YOLD 2014-3-23 (month 5 - 2 = month 3, day 26 - 3 = day 23)
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        DiscordianDate expectedDate = DiscordianDate.of(2014, 3, 23);
        DiscordianDate result = startDate.minus(DiscordianChronology.INSTANCE.period(0, 2, 3));
        assertEquals(expectedDate, result);
    }
}
