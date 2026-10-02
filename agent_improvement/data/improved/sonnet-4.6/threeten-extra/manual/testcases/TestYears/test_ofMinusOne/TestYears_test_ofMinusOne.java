package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestYears_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        Years minusOneYear = Years.of(-1);

        assertEquals(-1, minusOneYear.getAmount());
        assertTrue(minusOneYear.isNegative());
        assertFalse(minusOneYear.isZero());
        assertFalse(minusOneYear.isPositive());
    }
}
