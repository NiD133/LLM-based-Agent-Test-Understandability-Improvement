package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    // Zstandard skippable frame: first byte must have upper nibble 0x5 (range 0x50-0x5F),
    // followed by the three-byte magic suffix 0x2A 0x4D 0x18. Total minimum: 4 bytes.
    private static final int SKIPPABLE_FRAME_MIN_LENGTH = 4;

    @Test
    void testMatchesSkippableFrame() {
        // Prepare a buffer whose bytes 1-3 match the skippable frame magic suffix.
        // First byte 0x00 does not have upper nibble 0x5, so this is not yet a valid frame.
        final byte[] signature = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

        // With an invalid first byte the signature must not match
        assertFalse(ZstdUtils.matches(signature, SKIPPABLE_FRAME_MIN_LENGTH));

        // Any first byte in the range 0x50-0x5F (upper nibble == 0x5) makes a valid skippable frame
        for (byte b = (byte) 0x50; b < 0x60; b++) {
            signature[0] = b;
            assertTrue(ZstdUtils.matches(signature, SKIPPABLE_FRAME_MIN_LENGTH));
        }

        // Length 3 is below the 4-byte minimum, so the signature must not match
        assertFalse(ZstdUtils.matches(signature, SKIPPABLE_FRAME_MIN_LENGTH - 1));

        // A length greater than the minimum still produces a valid match
        assertTrue(ZstdUtils.matches(signature, SKIPPABLE_FRAME_MIN_LENGTH + 1));
    }
}
