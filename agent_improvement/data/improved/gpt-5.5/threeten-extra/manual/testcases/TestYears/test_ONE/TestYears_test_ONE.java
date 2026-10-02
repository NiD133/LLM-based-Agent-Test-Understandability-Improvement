package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestYears_test_ONE {

    @Test
    public void test_ONE() {
        Years oneYear = Years.of(1);

        assertSame(Years.ONE, oneYear);
        assertEquals(Years.ONE, oneYear);
        assertEquals(1, Years.ONE.getAmount());
        assertFalse(Years.ONE.isNegative());
        assertFalse(Years.ONE.isZero());
        assertTrue(Years.ONE.isPositive());
    }
}
