package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestYears_test_negated {

    @Test
    @DisplayName("negated() returns the arithmetic negation of the year amount")
    public void test_negated() {
        // Zero negated stays zero
        assertEquals(Years.of(0), Years.of(0).negated());

        // Positive value becomes negative
        assertEquals(Years.of(-12), Years.of(12).negated());

        // Negative value becomes positive
        assertEquals(Years.of(12), Years.of(-12).negated());

        // Maximum positive value negates without overflow
        assertEquals(Years.of(-Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).negated());
    }
}
