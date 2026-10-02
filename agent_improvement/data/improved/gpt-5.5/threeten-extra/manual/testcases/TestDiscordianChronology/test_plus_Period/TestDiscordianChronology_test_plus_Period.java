package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        DiscordianDate expectedDate = DiscordianDate.of(2015, 2, 29);

        assertEquals(expectedDate, startDate.plus(DiscordianChronology.INSTANCE.period(0, 2, 3)));
    }
}
