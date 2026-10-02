package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testConstructors {

    @Test
    void testConstructors() {
        // Default constructor: upper-case alphabet, lenient decoding
        assertDoesNotThrow(() -> new Base16());

        // Single-argument constructor: controls upper/lower-case alphabet
        assertDoesNotThrow(() -> new Base16(false)); // upper-case
        assertDoesNotThrow(() -> new Base16(true));  // lower-case

        // Two-argument constructor: controls alphabet case and decoding strictness
        assertDoesNotThrow(() -> new Base16(false, CodecPolicy.LENIENT));
        assertDoesNotThrow(() -> new Base16(false, CodecPolicy.STRICT));
    }
}
