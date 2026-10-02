package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#of(int)}.
 * <p>
 * Verifies that the factory stores the supplied minute value unchanged,
 * across zero, small positive/negative values and the integer extremes.
 */
public class TestMinutes_test_of {

    @Test
    public void of_storesTheGivenNumberOfMinutes() {
        // Zero and small positive values.
        assertEquals(0, Minutes.of(0).getAmount());
        assertEquals(1, Minutes.of(1).getAmount());
        assertEquals(2, Minutes.of(2).getAmount());

        // Small negative values.
        assertEquals(-1, Minutes.of(-1).getAmount());
        assertEquals(-2, Minutes.of(-2).getAmount());

        // Integer extremes.
        assertEquals(Integer.MAX_VALUE, Minutes.of(Integer.MAX_VALUE).getAmount());
        assertEquals(Integer.MIN_VALUE, Minutes.of(Integer.MIN_VALUE).getAmount());
    }
}
