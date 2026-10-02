package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testToString {

    @Test
    void testToString() {
        // Verify that toString() completes without error and returns a non-empty string.
        assertFalse(new InterpolatorStringLookup().toString().isEmpty());
    }
}
