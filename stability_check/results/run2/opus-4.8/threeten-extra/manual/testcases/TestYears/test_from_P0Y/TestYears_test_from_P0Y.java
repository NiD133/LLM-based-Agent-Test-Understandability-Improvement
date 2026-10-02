package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#from(java.time.temporal.TemporalAmount)} converts a
 * zero-year {@link Period} into {@code Years.of(0)}.
 */
public class TestYears_test_from_P0Y {

    @Test
    public void from_periodOfZeroYears_returnsZeroYears() {
        Years expected = Years.of(0);
        Years actual = Years.from(Period.ofYears(0));

        assertEquals(expected, actual);
    }
}
