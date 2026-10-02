package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests equality semantics for {@link JsonAutoDetect.Value}:
 * a "no overrides" instance (all visibilities set to DEFAULT) must be
 * unequal to the "default visibility" instance (which carries real visibility
 * levels), and each instance must be equal to itself.
 */
public class JsonAutoDetectTest_testEquality extends AnnotationTestUtil {

    // All visibility fields set to Visibility.DEFAULT — signals "no override".
    private static final JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    // Carries real visibility levels (e.g. PUBLIC_ONLY for fields/getters).
    private static final JsonAutoDetect.Value DEFAULT_VISIBILITY = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testEquality() {
        // Each instance is equal to itself (reflexivity).
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULT_VISIBILITY, DEFAULT_VISIBILITY);

        // The two distinct configurations must not be considered equal.
        assertFalse(DEFAULT_VISIBILITY.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULT_VISIBILITY));
    }
}
