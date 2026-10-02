package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#toString()}.
 * <p>
 * Each {@code Half} constant should print its own name, i.e. "H1" and "H2".
 */
public class TestHalf_test_toString {

    @Test
    public void toString_returnsEnumName() {
        assertEquals("H1", Half.H1.toString());
        assertEquals("H2", Half.H2.toString());
    }
}
