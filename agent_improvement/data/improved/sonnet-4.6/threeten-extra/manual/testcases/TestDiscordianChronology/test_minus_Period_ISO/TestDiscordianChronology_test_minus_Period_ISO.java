package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link DiscordianDate} throws
 * {@link DateTimeException}, because ISO periods are not compatible with the Discordian calendar.
 */
public class TestDiscordianChronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        // Period.ofMonths() returns an ISO period; subtracting it from a DiscordianDate
        // must be rejected with DateTimeException since the chronologies differ.
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
