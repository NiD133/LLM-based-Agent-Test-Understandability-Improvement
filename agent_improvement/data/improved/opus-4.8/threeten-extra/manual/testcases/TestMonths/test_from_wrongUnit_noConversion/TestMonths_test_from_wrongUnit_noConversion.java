package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#from(java.time.temporal.TemporalAmount)} rejects an
 * amount whose only unit (days) cannot be converted to whole months.
 */
public class TestMonths_test_from_wrongUnit_noConversion {

    @Test
    public void from_periodOfDays_throwsBecauseDaysCannotConvertToMonths() {
        Period twoDays = Period.ofDays(2);

        assertThrows(DateTimeException.class, () -> Months.from(twoDays));
    }
}
