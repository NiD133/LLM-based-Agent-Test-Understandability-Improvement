package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link DiscordianDate} rejects subtraction of an ISO {@link Period}.
 * <p>
 * The Discordian calendar does not share the ISO month structure, so subtracting an
 * ISO-based period (here, 2 ISO months) is unsupported and must raise a
 * {@link DateTimeException}.
 */
public class TestDiscordianChronology_test_minus_Period_ISO {

    @Test
    public void minus_isoPeriod_throwsDateTimeException() {
        DiscordianDate date = DiscordianDate.of(2014, 5, 26);
        Period twoIsoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.minus(twoIsoMonths));
    }
}
