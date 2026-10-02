package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InterpolatorStringLookup#toString()}.
 */
public class InterpolatorStringLookupTest_testToString {

    /**
     * Verifies that {@code toString()} returns a non-empty string and does not throw.
     */
    @Test
    void testToString() {
        final String description = new InterpolatorStringLookup().toString();

        assertFalse(description.isEmpty(), "toString() should return a non-empty description");
    }
}
