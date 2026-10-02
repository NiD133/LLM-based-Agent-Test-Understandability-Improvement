package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding an ISO {@link Period} to a {@link DiscordianDate} is rejected.
 * <p>
 * A Discordian year has 5 months while an ISO {@code Period} counts months in the
 * ISO calendar, so the two are incompatible. Attempting to add an ISO period that
 * carries a month component must therefore fail with a {@link DateTimeException}.
 */
public class TestDiscordianChronology_test_plus_Period_ISO {

    @Test
    public void plus_isoPeriodWithMonths_throwsDateTimeException() {
        DiscordianDate date = DiscordianDate.of(2014, 5, 26);
        Period isoTwoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.plus(isoTwoMonths));
    }
}
