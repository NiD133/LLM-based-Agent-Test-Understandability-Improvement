package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Verifies the {@code equals} contract for the two predefined
 * {@link JsonAutoDetect.Value} instances:
 * <ul>
 *   <li>{@code noOverrides()} – every accessor set to {@code Visibility.DEFAULT}</li>
 *   <li>{@code defaultVisibility()} – the baseline visibility configuration</li>
 * </ul>
 */
public class JsonAutoDetectTest_testEquality extends AnnotationTestUtil {

    /** All accessors set to {@code Visibility.DEFAULT}; applies no overrides. */
    private static final JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    /** The baseline visibility configuration (public getters, all setters, etc.). */
    private static final JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testEquality() {
        // Each predefined Value must be equal to itself.
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULTS, DEFAULTS);

        // The two distinct configurations must never be equal, in either direction.
        assertFalse(DEFAULTS.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULTS));
    }
}
