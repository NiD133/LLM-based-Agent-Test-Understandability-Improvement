package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests that ZstdUtils.matches correctly identifies Zstandard skippable frames.
 *
 * A skippable frame starts with a first byte whose high nibble is 0x5 (i.e., 0x50–0x5F),
 * followed by the three-byte magic sequence {0x2A, 0x4D, 0x18}.
 * At least 4 bytes must be present for a valid match.
 */
public class ZstdUtilsTest_testMatchesSkippableFrame {

    // The three shared magic bytes that follow the variable first byte in a skippable frame header.
    private static final byte SKIPPABLE_MAGIC_BYTE_1 = (byte) 0x2A;
    private static final byte SKIPPABLE_MAGIC_BYTE_2 = (byte) 0x4D;
    private static final byte SKIPPABLE_MAGIC_BYTE_3 = (byte) 0x18;

    // Range of valid first bytes for a skippable frame: high nibble must be 0x5 (0x50–0x5F).
    private static final byte SKIPPABLE_FIRST_BYTE_MIN = (byte) 0x50;
    private static final byte SKIPPABLE_FIRST_BYTE_MAX = (byte) 0x60; // exclusive

    // Minimum required signature length to identify a frame (4 bytes).
    private static final int MINIMUM_MATCH_LENGTH = 4;

    @Test
    void testMatchesSkippableFrame() {
        // Build a candidate signature whose bytes 1-3 carry the skippable frame magic.
        // Byte 0 starts as 0x00, which has high nibble 0x0 — not a valid skippable-frame marker.
        final byte[] data = { 0, SKIPPABLE_MAGIC_BYTE_1, SKIPPABLE_MAGIC_BYTE_2, SKIPPABLE_MAGIC_BYTE_3 };

        // A first byte of 0x00 does not satisfy the 0x50-0x5F requirement — expect no match.
        assertFalse(ZstdUtils.matches(data, MINIMUM_MATCH_LENGTH));

        // Every first byte in the range 0x50–0x5F (high nibble == 0x5) must produce a match.
        for (byte b = SKIPPABLE_FIRST_BYTE_MIN; b < SKIPPABLE_FIRST_BYTE_MAX; b++) {
            data[0] = b;
            assertTrue(ZstdUtils.matches(data, MINIMUM_MATCH_LENGTH));
        }

        // Fewer than 4 bytes is not enough to identify the frame — expect no match.
        assertFalse(ZstdUtils.matches(data, MINIMUM_MATCH_LENGTH - 1));

        // Providing more bytes than the minimum (5 here) still matches — extra bytes are ignored.
        assertTrue(ZstdUtils.matches(data, MINIMUM_MATCH_LENGTH + 1));
    }
}
