package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#from(java.time.temporal.TemporalAmount)} rejects an
 * amount whose unit cannot be converted to a whole number of years.
 */
public class TestYears_test_from_wrongUnit_remainder {

    @Test
    public void from_periodOfMonthsThatIsNotAWholeYear_throwsDateTimeException() {
        // 3 months is not a whole number of years, so the conversion must fail.
        Period threeMonths = Period.ofMonths(3);

        assertThrows(DateTimeException.class, () -> Years.from(threeMonths));
    }
}
