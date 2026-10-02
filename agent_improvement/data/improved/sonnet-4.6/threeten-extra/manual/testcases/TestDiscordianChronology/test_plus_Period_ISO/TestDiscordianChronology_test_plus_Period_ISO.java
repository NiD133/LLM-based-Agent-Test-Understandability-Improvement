package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_plus_Period_ISO {

    // Adding an ISO Period to a DiscordianDate must fail because ISO periods
    // (which use ISO months) are not compatible with the Discordian calendar.
    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
