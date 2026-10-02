package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#abs()}, which returns the amount with its sign removed.
 */
public class TestMinutes_test_abs {

    @Test
    public void abs_returnsAbsoluteValueOfMinutes() {
        // Zero stays zero.
        assertEquals(Minutes.of(0), Minutes.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Minutes.of(12), Minutes.of(12).abs());

        // A negative amount has its sign removed.
        assertEquals(Minutes.of(12), Minutes.of(-12).abs());

        // The maximum positive amount is returned unchanged.
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).abs());

        // The most negative value that can still be negated yields its positive counterpart.
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(-Integer.MAX_VALUE).abs());
    }
}
