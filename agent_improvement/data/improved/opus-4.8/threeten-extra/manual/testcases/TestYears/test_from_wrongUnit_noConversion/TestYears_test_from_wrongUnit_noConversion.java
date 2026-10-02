package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#from(java.time.temporal.TemporalAmount)} rejects an
 * amount whose only unit (days) cannot be converted to whole years.
 */
public class TestYears_test_from_wrongUnit_noConversion {

    @Test
    public void from_periodOfDays_throwsBecauseDaysCannotConvertToYears() {
        Period twoDays = Period.ofDays(2);

        assertThrows(DateTimeException.class, () -> Years.from(twoDays));
    }
}
