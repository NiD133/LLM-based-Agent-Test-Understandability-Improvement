package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestMinutes_test_of {

    // Verifies that Minutes.of(n) stores and returns the exact minute count via getAmount().
    @Test
    public void test_of() {
        // zero
        assertEquals(0, Minutes.of(0).getAmount());

        // small positive values
        assertEquals(1, Minutes.of(1).getAmount());
        assertEquals(2, Minutes.of(2).getAmount());

        // small negative values
        assertEquals(-1, Minutes.of(-1).getAmount());
        assertEquals(-2, Minutes.of(-2).getAmount());

        // integer boundary values
        assertEquals(Integer.MAX_VALUE, Minutes.of(Integer.MAX_VALUE).getAmount());
        assertEquals(Integer.MIN_VALUE, Minutes.of(Integer.MIN_VALUE).getAmount());
    }
}
