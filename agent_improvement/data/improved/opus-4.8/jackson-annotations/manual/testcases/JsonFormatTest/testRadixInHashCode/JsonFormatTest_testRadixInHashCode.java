package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Verifies that the {@code radix} attribute participates in
 * {@link JsonFormat.Value#hashCode()}: two {@code Value}s that differ
 * only by their radix must not be equal, and their hash codes should
 * (very likely) differ as well.
 */
public class JsonFormatTest_testRadixInHashCode extends AnnotationTestUtil {

    @Test
    void testRadixInHashCode() {
        JsonFormat.Value binaryRadix = JsonFormat.Value.forRadix(2);
        JsonFormat.Value hexRadix = JsonFormat.Value.forRadix(16);

        // Differing radix => values are not equal ...
        assertNotEquals(binaryRadix, hexRadix);
        // ... and therefore their hash codes should differ too.
        assertNotEquals(binaryRadix.hashCode(), hexRadix.hashCode());
    }
}
