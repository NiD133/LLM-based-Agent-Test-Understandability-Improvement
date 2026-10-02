package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Half} enum constants themselves: that the standard
 * enum facilities ({@code valueOf} and {@code values}) expose H1 and H2
 * as expected.
 */
public class TestHalf_test_enum {

    @Test
    public void valueOf_and_values_exposeHalfConstants() {
        // valueOf("H2") must resolve to the H2 constant
        assertEquals(Half.H2, Half.valueOf("H2"));
        // H1 is declared first, so it is the first element of values()
        assertEquals(Half.H1, Half.values()[0]);
    }
}
