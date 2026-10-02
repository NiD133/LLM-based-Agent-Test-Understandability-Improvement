package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#from(java.time.temporal.TemporalAmount)} with a month-based period.
 */
public class TestYears_test_from_P24M {

    @Test
    public void from_periodOf24Months_returns2Years() {
        // 24 months convert exactly to 2 years (12 months == 1 year).
        Years result = Years.from(Period.ofMonths(24));

        assertEquals(Years.of(2), result);
    }
}
