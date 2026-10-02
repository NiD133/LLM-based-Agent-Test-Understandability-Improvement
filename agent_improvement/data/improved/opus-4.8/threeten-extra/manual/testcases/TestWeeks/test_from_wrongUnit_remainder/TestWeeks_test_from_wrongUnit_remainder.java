package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#from(java.time.temporal.TemporalAmount)} rejects an
 * amount whose unit does not divide evenly into whole weeks.
 */
public class TestWeeks_test_from_wrongUnit_remainder {

    @Test
    public void from_periodOfDays_notWholeNumberOfWeeks_throwsException() {
        // 3 days is not a whole number of weeks, so the conversion must fail.
        assertThrows(DateTimeException.class, () -> Weeks.from(Period.ofDays(3)));
    }
}
