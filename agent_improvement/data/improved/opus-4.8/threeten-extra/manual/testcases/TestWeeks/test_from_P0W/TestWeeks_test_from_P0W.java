package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#from(java.time.temporal.TemporalAmount)}.
 */
public class TestWeeks_test_from_P0W {

    /**
     * Converting a zero-week {@link Period} (the ISO-8601 amount "P0W") with
     * {@link Weeks#from} should yield {@link Weeks} representing zero weeks.
     */
    @Test
    public void from_zeroWeekPeriod_returnsZeroWeeks() {
        Period zeroWeekPeriod = Period.ofWeeks(0);

        Weeks result = Weeks.from(zeroWeekPeriod);

        assertEquals(Weeks.of(0), result);
    }
}
