package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} preserves leading zero bytes across an
 * encode/decode round trip.
 *
 * <p>In Base58 every leading zero byte is represented by a single '1'
 * character at the start of the encoded text, and decoding must turn those
 * '1' characters back into the original leading zero bytes.</p>
 */
public class Base58Test_testLeadingZeros {

    @Test
    void testLeadingZeros() {
        // Input begins with two zero bytes, which should survive the round trip.
        final byte[] original = { 0, 0, 1, 2, 3 };

        // Encoding: each of the two leading zero bytes becomes a '1' character.
        final byte[] encoded = new Base58().encode(original);
        final String encodedText = new String(encoded);
        assertTrue(encodedText.startsWith("11"),
                "Two leading zero bytes should be encoded as two '1' characters");

        // Decoding: the '1' characters must be restored to the leading zero bytes.
        final byte[] decoded = new Base58().decode(encoded);
        assertArrayEquals(original, decoded,
                "Decoded bytes should match the original, including the leading zeros");
    }
}
