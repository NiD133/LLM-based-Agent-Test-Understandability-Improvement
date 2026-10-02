package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testRadix extends AnnotationTestUtil {

    private static final int BINARY_RADIX = 2;

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void nonDefaultRadixOverridesEmptyValueOnMerge() {
        JsonFormat.Value binaryRadixValue = JsonFormat.Value.forRadix(BINARY_RADIX);

        JsonFormat.Value merged = EMPTY.withOverrides(binaryRadixValue);

        assertEquals(DEFAULT_RADIX, EMPTY.getRadix(),
            "Empty value should retain the default radix");
        assertEquals(BINARY_RADIX, merged.getRadix(),
            "Non-default radix from override should take precedence over empty value's default radix");
    }

    @Test
    void defaultRadixDoesNotOverrideNonDefaultRadixOnMerge() {
        JsonFormat.Value binaryRadixValue = JsonFormat.Value.forRadix(BINARY_RADIX);

        JsonFormat.Value merged = binaryRadixValue.withOverrides(EMPTY);

        assertEquals(BINARY_RADIX, binaryRadixValue.getRadix(),
            "Original value's radix should be unchanged");
        assertEquals(BINARY_RADIX, merged.getRadix(),
            "Default radix from override should not replace an existing non-default radix");
    }

    @Test
    void withRadixProducesValueWithSpecifiedRadix() {
        JsonFormat.Value valueWithBinaryRadix = EMPTY.withRadix(BINARY_RADIX);

        assertEquals(BINARY_RADIX, valueWithBinaryRadix.getRadix());
    }

    @Test
    void forRadixFactoryProducesValueWithSpecifiedRadix() {
        JsonFormat.Value valueForBinaryRadix = JsonFormat.Value.forRadix(BINARY_RADIX);

        assertEquals(BINARY_RADIX, valueForBinaryRadix.getRadix());
    }
}
