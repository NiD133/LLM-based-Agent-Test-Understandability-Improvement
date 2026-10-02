package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ZstdUtils#matches(byte[], int)} correctly identifies
 * the Zstandard frame magic bytes: {@code 0x28 0xB5 0x2F 0xFD}.
 *
 * <p>The Zstandard frame magic is exactly 4 bytes, so {@code matches} requires
 * {@code length >= 4} and all four bytes to be correct.</p>
 */
public class ZstdUtilsTest_testMatchesZstandardFrame {

    /** The Zstandard frame magic number — four bytes that begin every valid Zstandard frame. */
    private static final byte[] ZSTD_FRAME_MAGIC = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD };

    @Test
    void testMatchesZstandardFrame() {
        final byte[] data = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD };

        // Length 3 is less than the 4-byte magic → no match
        assertFalse(ZstdUtils.matches(data, 3),
                "Should not match when fewer than 4 bytes are provided");

        // Exactly 4 bytes with correct magic → match
        assertTrue(ZstdUtils.matches(data, 4),
                "Should match the valid Zstandard frame magic with length == 4");

        // More than 4 bytes still satisfies the minimum length requirement → match
        assertTrue(ZstdUtils.matches(data, 5),
                "Should match the valid Zstandard frame magic with length > 4");

        // Corrupt the last magic byte so the signature no longer matches
        data[3] = '0'; // '0' == 0x30, not the expected 0xFD
        assertFalse(ZstdUtils.matches(data, 4),
                "Should not match after the 4th magic byte is corrupted");
    }
}
