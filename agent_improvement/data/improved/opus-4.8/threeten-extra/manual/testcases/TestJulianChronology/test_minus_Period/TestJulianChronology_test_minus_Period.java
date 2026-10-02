package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;
import org.junit.jupiter.api.Test;

/**
 * Tests subtracting a Julian {@link ChronoPeriod} from a {@link JulianDate}.
 */
public class TestJulianChronology_test_minus_Period {

    @Test
    public void minus_period_subtractsYearsMonthsAndDays() {
        // Start from 26 May 2014 (Julian).
        JulianDate startDate = JulianDate.of(2014, 5, 26);

        // Subtract a period of 0 years, 2 months and 3 days.
        ChronoPeriod periodToSubtract = JulianChronology.INSTANCE.period(0, 2, 3);
        JulianDate result = startDate.minus(periodToSubtract);

        // 26 May 2014 minus 2 months and 3 days is 23 March 2014.
        assertEquals(JulianDate.of(2014, 3, 23), result);
    }
}
