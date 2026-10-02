package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Years#ONE} constant.
 */
public class TestYears_test_ONE {

    @Test
    public void test_ONE() {
        // ONE is the cached singleton returned by of(1).
        assertSame(Years.ONE, Years.of(1));
        assertEquals(Years.ONE, Years.of(1));

        // ONE represents exactly one (positive, non-zero) year.
        assertEquals(1, Years.ONE.getAmount());
        assertFalse(Years.ONE.isNegative());
        assertFalse(Years.ONE.isZero());
        assertTrue(Years.ONE.isPositive());
    }
}
