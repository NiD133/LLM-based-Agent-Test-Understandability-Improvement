package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ZstdUtils#matches(byte[], int)} against the four-byte
 * Zstandard frame magic number {@code 28 B5 2F FD}.
 */
public class ZstdUtilsTest_testMatchesZstandardFrame {

    /** The four magic bytes that mark the start of a Zstandard frame. */
    private static final byte[] ZSTANDARD_FRAME_MAGIC = {
        (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD
    };

    @Test
    void testMatchesZstandardFrame() {
        final byte[] signature = ZSTANDARD_FRAME_MAGIC.clone();

        // Fewer bytes than the magic number is never a match.
        assertFalse(ZstdUtils.matches(signature, 3),
            "3 bytes is shorter than the 4-byte magic and must not match");

        // The exact magic number, and any longer prefix of it, is a match.
        assertTrue(ZstdUtils.matches(signature, 4),
            "the full 4-byte magic must match");
        assertTrue(ZstdUtils.matches(signature, 5),
            "a length beyond the magic still matches the leading magic bytes");

        // Corrupting the last magic byte breaks the match.
        signature[3] = '0';
        assertFalse(ZstdUtils.matches(signature, 4),
            "a wrong final magic byte must not match");
    }
}
