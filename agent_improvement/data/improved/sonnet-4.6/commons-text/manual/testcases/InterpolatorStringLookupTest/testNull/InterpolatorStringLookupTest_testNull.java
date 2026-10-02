package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testNull {

    @Test
    void testNull() {
        // Passing null to the singleton interpolator should return null without throwing.
        assertNull(InterpolatorStringLookup.INSTANCE.apply(null));
    }
}
