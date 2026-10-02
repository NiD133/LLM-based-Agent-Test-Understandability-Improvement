package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#of(int)}, the factory that maps an {@code int} value to a {@code Half}.
 */
public class TestHalf_test_of_int {

    @Test
    public void test_of_int() {
        // Valid values 1 and 2 map to the matching half-of-year.
        assertEquals(1, Half.of(1).getValue());
        assertEquals(2, Half.of(2).getValue());

        // Any value outside the range 1..2 is rejected.
        assertThrows(DateTimeException.class, () -> Half.of(0));
        assertThrows(DateTimeException.class, () -> Half.of(3));
    }
}
