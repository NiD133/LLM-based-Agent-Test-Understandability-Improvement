package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JsonFormatTest_testRadixInHashCode extends AnnotationTestUtil {

    private static final int BINARY_RADIX = 2;
    private static final int HEXADECIMAL_RADIX = 16;

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testRadixInHashCode() {
        JsonFormat.Value binaryRadixFormat = JsonFormat.Value.forRadix(BINARY_RADIX);
        JsonFormat.Value hexadecimalRadixFormat = JsonFormat.Value.forRadix(HEXADECIMAL_RADIX);

        assertNotEquals(binaryRadixFormat, hexadecimalRadixFormat);

        // Different radix values participate in the hash code calculation.
        assertNotEquals(binaryRadixFormat.hashCode(), hexadecimalRadixFormat.hashCode());
    }
}
