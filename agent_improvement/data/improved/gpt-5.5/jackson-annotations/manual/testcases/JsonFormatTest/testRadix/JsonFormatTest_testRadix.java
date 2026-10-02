package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testRadix extends AnnotationTestUtil {

    private static final int BINARY_RADIX = 2;

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testRadix() {
        JsonFormat.Value binaryRadixOverride = JsonFormat.Value.forRadix(BINARY_RADIX);
        assertNonDefaultRadixOverridesEmpty(binaryRadixOverride);

        JsonFormat.Value binaryRadixBase = JsonFormat.Value.forRadix(BINARY_RADIX);
        assertEmptyDoesNotOverrideNonDefaultRadix(binaryRadixBase);

        assertWithRadixUsesRequestedRadix();
        assertForRadixUsesRequestedRadix();
    }

    private void assertNonDefaultRadixOverridesEmpty(JsonFormat.Value override) {
        JsonFormat.Value merged = EMPTY.withOverrides(override);

        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());
    }

    private void assertEmptyDoesNotOverrideNonDefaultRadix(JsonFormat.Value base) {
        JsonFormat.Value merged = base.withOverrides(EMPTY);

        assertEquals(BINARY_RADIX, base.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());
    }

    private void assertWithRadixUsesRequestedRadix() {
        JsonFormat.Value emptyWithBinaryRadix = EMPTY.withRadix(BINARY_RADIX);

        assertEquals(BINARY_RADIX, emptyWithBinaryRadix.getRadix());
    }

    private void assertForRadixUsesRequestedRadix() {
        JsonFormat.Value forBinaryRadix = JsonFormat.Value.forRadix(BINARY_RADIX);

        assertEquals(BINARY_RADIX, forBinaryRadix.getRadix());
    }
}
