package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LZMAUtils#matches(byte[], int)}, which reports whether the first
 * bytes of a signature equal the three LZMA header magic bytes {0x5D, 0x00, 0x00}.
 */
public class LZMAUtilsTest_testMatches {

    /** The three LZMA header magic bytes that {@code matches} looks for. */
    private static final int MAGIC_LENGTH = 3;

    @Test
    void testMatches() {
        // A signature whose first three bytes are exactly the LZMA magic bytes.
        final byte[] signature = { (byte) 0x5D, 0, 0 };

        // Fewer bytes than the magic header are available: cannot match.
        assertFalse(LZMAUtils.matches(signature, MAGIC_LENGTH - 1),
                "length shorter than the magic header must not match");

        // Exactly the magic bytes are present: matches.
        assertTrue(LZMAUtils.matches(signature, MAGIC_LENGTH),
                "the three magic bytes must match");

        // A length beyond the magic header still matches; trailing bytes are ignored.
        assertTrue(LZMAUtils.matches(signature, MAGIC_LENGTH + 1),
                "extra length beyond the magic bytes must still match");

        // Altering one of the magic bytes breaks the match.
        signature[2] = '0';
        assertFalse(LZMAUtils.matches(signature, MAGIC_LENGTH),
                "a changed magic byte must not match");
    }
}
