package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        ChronoPeriod amountToSubtract = DiscordianChronology.INSTANCE.period(0, 2, 3);
        DiscordianDate expectedDate = DiscordianDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(amountToSubtract));
    }
}
