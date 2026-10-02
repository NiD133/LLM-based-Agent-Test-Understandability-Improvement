package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how the {@code radix} setting behaves on {@link JsonFormat.Value}:
 * how it is read, how it is set, and how it is combined during a merge
 * (via {@link JsonFormat.Value#withOverrides}).
 */
public class JsonFormatTest_testRadix extends AnnotationTestUtil {

    /** Base value carrying no explicit settings; its radix is the default. */
    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    /** A non-default radix used throughout the test (binary representation). */
    private static final int BINARY_RADIX = 2;

    @Test
    void testRadix() {
        // An empty value reports the default radix.
        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());

        // Merging in a non-default radix overrides the default one.
        JsonFormat.Value binaryRadix = JsonFormat.Value.forRadix(BINARY_RADIX);
        JsonFormat.Value defaultOverriddenByBinary = EMPTY.withOverrides(binaryRadix);
        assertEquals(BINARY_RADIX, defaultOverriddenByBinary.getRadix());

        // Merging in an empty (default-radix) value does NOT override an existing radix.
        JsonFormat.Value binaryOverriddenByDefault = binaryRadix.withOverrides(EMPTY);
        assertEquals(BINARY_RADIX, binaryRadix.getRadix());
        assertEquals(BINARY_RADIX, binaryOverriddenByDefault.getRadix());

        // withRadix(...) produces a value with the requested radix.
        assertEquals(BINARY_RADIX, EMPTY.withRadix(BINARY_RADIX).getRadix());

        // forRadix(...) produces a value with the requested radix.
        assertEquals(BINARY_RADIX, JsonFormat.Value.forRadix(BINARY_RADIX).getRadix());
    }
}
