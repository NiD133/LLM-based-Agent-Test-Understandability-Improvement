package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#toPeriod()}.
 */
public class TestDays_test_toPeriod {

    /**
     * {@code toPeriod()} should produce a {@link Period} holding exactly the same
     * number of days, for every value across a representative negative-to-positive range.
     */
    @Test
    public void test_toPeriod() {
        for (int numberOfDays = -20; numberOfDays < 20; numberOfDays++) {
            Period expected = Period.ofDays(numberOfDays);
            Period actual = Days.of(numberOfDays).toPeriod();

            assertEquals(expected, actual);
        }
    }
}
