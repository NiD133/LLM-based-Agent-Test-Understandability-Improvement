package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ZstdUtils#matches(byte[], int)} recognition of Zstandard
 * "skippable frames".
 *
 * <p>A skippable frame is identified by a 4-byte magic number whose first byte
 * has the high nibble {@code 0x5} (i.e. any value in the range 0x50..0x5F),
 * followed by the three constant bytes {@code 0x2A 0x4D 0x18}.</p>
 */
public class ZstdUtilsTest_testMatchesSkippableFrame {

    /** The three constant trailing bytes shared by every skippable frame magic number. */
    private static final byte[] SKIPPABLE_FRAME_TRAILER = { (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

    /** Lowest value of the skippable frame's leading byte (inclusive). */
    private static final byte FIRST_LEADING_BYTE = (byte) 0x50;

    /** One past the highest valid value of the skippable frame's leading byte. */
    private static final byte AFTER_LAST_LEADING_BYTE = (byte) 0x60;

    @Test
    void testMatchesSkippableFrame() {
        // A signature carrying the correct trailer but a leading byte of 0 is not a skippable frame.
        final byte[] signature = { 0, SKIPPABLE_FRAME_TRAILER[0], SKIPPABLE_FRAME_TRAILER[1], SKIPPABLE_FRAME_TRAILER[2] };
        assertFalse(ZstdUtils.matches(signature, 4), "leading byte 0x00 must not be recognized as a skippable frame");

        // Every leading byte in 0x50..0x5F combined with the trailer is a valid skippable frame.
        for (byte leadingByte = FIRST_LEADING_BYTE; leadingByte < AFTER_LAST_LEADING_BYTE; leadingByte++) {
            signature[0] = leadingByte;
            assertTrue(ZstdUtils.matches(signature, 4),
                    "leading byte in 0x50..0x5F must be recognized as a skippable frame");
        }

        // A length shorter than the 4-byte magic number never matches, regardless of content.
        assertFalse(ZstdUtils.matches(signature, 3), "length below 4 must never match");

        // A length longer than 4 still matches; only the first four bytes are inspected.
        assertTrue(ZstdUtils.matches(signature, 5), "length above 4 still matches on the leading magic bytes");
    }
}
