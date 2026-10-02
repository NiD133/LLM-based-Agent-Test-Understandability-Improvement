package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the standard enum behaviour of {@link Quarter}.
 */
public class TestQuarter_test_enum {

    @Test
    public void valueOf_returnsMatchingConstant() {
        // valueOf("Q4") must resolve to the Q4 enum constant.
        assertEquals(Quarter.Q4, Quarter.valueOf("Q4"));
    }

    @Test
    public void values_arePresentInDeclarationOrder() {
        // Q1 is declared first, so it must occupy index 0 of values().
        assertEquals(Quarter.Q1, Quarter.values()[0]);
    }
}
