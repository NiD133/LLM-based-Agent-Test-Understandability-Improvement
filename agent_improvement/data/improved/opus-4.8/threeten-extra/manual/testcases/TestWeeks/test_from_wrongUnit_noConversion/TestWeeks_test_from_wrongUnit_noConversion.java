package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#from(java.time.temporal.TemporalAmount)} rejects a
 * temporal amount whose unit cannot be converted to whole weeks.
 */
public class TestWeeks_test_from_wrongUnit_noConversion {

    @Test
    public void from_periodInMonths_throwsBecauseMonthsCannotConvertToWeeks() {
        // A period of 2 months has no whole-week equivalent, so conversion must fail.
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofMonths(2)));
    }
}
