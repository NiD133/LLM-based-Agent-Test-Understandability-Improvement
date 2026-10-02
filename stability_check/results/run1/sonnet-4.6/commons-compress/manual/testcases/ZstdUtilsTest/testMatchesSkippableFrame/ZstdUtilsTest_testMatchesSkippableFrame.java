package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    // Zstandard skippable frames have a 4-byte magic: first byte must have upper nibble 0x5
    // (i.e., values 0x50–0x5F), followed by the three fixed bytes 0x2A, 0x4D, 0x18.
    private static final byte SKIPPABLE_MAGIC_BYTE_1 = (byte) 0x2A;
    private static final byte SKIPPABLE_MAGIC_BYTE_2 = (byte) 0x4D;
    private static final byte SKIPPABLE_MAGIC_BYTE_3 = (byte) 0x18;

    // The valid range for the first byte of a skippable frame: upper nibble must be 0x5
    private static final byte SKIPPABLE_FIRST_BYTE_MIN = (byte) 0x50;
    private static final byte SKIPPABLE_FIRST_BYTE_MAX = (byte) 0x5F;

    // Minimum number of bytes required for ZstdUtils.matches() to consider any frame
    private static final int MINIMUM_MATCH_LENGTH = 4;

    @Test
    void testMatchesSkippableFrame() {
        // Build a signature with the three fixed skippable-frame bytes in positions 1–3.
        // Position 0 is initially 0x00, which does NOT satisfy the upper-nibble-0x5 requirement.
        final byte[] signature = { 0, SKIPPABLE_MAGIC_BYTE_1, SKIPPABLE_MAGIC_BYTE_2, SKIPPABLE_MAGIC_BYTE_3 };

        // A first byte of 0x00 does not match any Zstandard frame format
        assertFalse(ZstdUtils.matches(signature, MINIMUM_MATCH_LENGTH));

        // Every first byte in 0x50–0x5F (upper nibble = 0x5) represents a valid skippable frame
        for (byte firstByte = SKIPPABLE_FIRST_BYTE_MIN; firstByte < (byte) 0x60; firstByte++) {
            signature[0] = firstByte;
            assertTrue(ZstdUtils.matches(signature, MINIMUM_MATCH_LENGTH));
        }

        // Fewer than 4 bytes available: too short to identify any frame, must not match
        assertFalse(ZstdUtils.matches(signature, MINIMUM_MATCH_LENGTH - 1));

        // More than 4 bytes available: still matches, extra length does not invalidate the signature
        assertTrue(ZstdUtils.matches(signature, MINIMUM_MATCH_LENGTH + 1));
    }
}
