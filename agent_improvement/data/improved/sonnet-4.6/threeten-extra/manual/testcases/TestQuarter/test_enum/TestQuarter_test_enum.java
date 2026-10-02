package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_enum {

    /**
     * Verifies the standard enum contract for Quarter:
     *  - valueOf returns the correct constant when given the enum name as a String.
     *  - values() returns constants in declaration order, so Q1 occupies index 0.
     */
    @Test
    public void test_enum() {
        assertEquals(Quarter.Q4, Quarter.valueOf("Q4"));
        assertEquals(Quarter.Q1, Quarter.values()[0]);
    }
}
