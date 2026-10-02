package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} handles empty and {@code null} inputs gracefully
 * for both encoding and decoding.
 */
public class Base58Test_testEmptyBase58 {

    @Test
    void testEmptyBase58() {
        final Base58 base58 = new Base58();

        // Encoding empty input yields an empty result; null input yields null.
        assertEquals(0, base58.encode(new byte[0]).length, "encoding empty input should produce an empty array");
        assertNull(base58.encode(null), "encoding null input should produce null");

        // Decoding empty input yields an empty result; null input yields null.
        assertEquals(0, base58.decode(new byte[0]).length, "decoding empty input should produce an empty array");
        assertNull(base58.decode((byte[]) null), "decoding null input should produce null");
    }
}
