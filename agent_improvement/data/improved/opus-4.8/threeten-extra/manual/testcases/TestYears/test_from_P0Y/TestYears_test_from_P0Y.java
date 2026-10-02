package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#from(java.time.temporal.TemporalAmount)} converts a
 * zero-year {@link Period} into the canonical {@code Years} instance for zero.
 */
public class TestYears_test_from_P0Y {

    @Test
    public void from_periodOfZeroYears_returnsZeroYears() {
        Years result = Years.from(Period.ofYears(0));

        assertEquals(Years.of(0), result);
    }
}
