package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#from(java.time.temporal.TemporalAmount)}.
 */
public class TestWeeks_test_from_P14D {

    /**
     * A 14-day period spans exactly two whole weeks, so {@code Weeks.from}
     * should convert it to {@code Weeks.of(2)}.
     */
    @Test
    public void from_periodOf14Days_returnsTwoWeeks() {
        Weeks expected = Weeks.of(2);
        Weeks actual = Weeks.from(Period.ofDays(14));

        assertEquals(expected, actual);
    }
}
