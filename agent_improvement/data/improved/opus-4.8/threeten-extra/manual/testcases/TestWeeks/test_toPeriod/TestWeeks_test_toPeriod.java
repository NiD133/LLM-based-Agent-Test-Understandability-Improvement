package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#toPeriod()}.
 */
public class TestWeeks_test_toPeriod {

    /**
     * {@code Weeks.of(n).toPeriod()} must equal {@code Period.ofWeeks(n)}
     * for every supported number of weeks, including negative and zero values.
     */
    @Test
    public void test_toPeriod() {
        for (int weeks = -20; weeks < 20; weeks++) {
            Period expected = Period.ofWeeks(weeks);
            Period actual = Weeks.of(weeks).toPeriod();
            assertEquals(expected, actual);
        }
    }
}
