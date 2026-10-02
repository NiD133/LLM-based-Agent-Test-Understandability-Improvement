package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ZstdUtils#matches(byte[], int)} for the Zstandard "skippable frame" magic bytes.
 *
 * <p>
 * A skippable frame is recognized when:
 * </p>
 * <ol>
 *   <li>the first byte has the high nibble {@code 0x5} (i.e. the byte lies in the range {@code 0x50}..{@code 0x5F}), and</li>
 *   <li>the following three bytes equal the skippable-frame magic {@code 0x2A 0x4D 0x18}, and</li>
 *   <li>at least four bytes are available to inspect.</li>
 * </ol>
 */
public class ZstdUtilsTest_testMatchesSkippableFrame {

    /** Number of leading bytes that identify a skippable frame (1 marker byte + 3 magic bytes). */
    private static final int SKIPPABLE_FRAME_LENGTH = 4;

    /** First value (inclusive) whose high nibble marks a skippable frame. */
    private static final byte FIRST_SKIPPABLE_MARKER = (byte) 0x50;

    /** First value (exclusive) past the skippable-frame marker range. */
    private static final byte AFTER_LAST_SKIPPABLE_MARKER = (byte) 0x60;

    @Test
    void testMatchesSkippableFrame() {
        // Bytes 1..3 hold the skippable-frame magic; byte 0 is the marker we vary below.
        final byte[] signature = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

        // Marker 0x00 does not have the required high nibble, so this is not a skippable frame.
        assertFalse(ZstdUtils.matches(signature, SKIPPABLE_FRAME_LENGTH));

        // Every marker byte in 0x50..0x5F combined with the magic bytes is a valid skippable frame.
        for (byte marker = FIRST_SKIPPABLE_MARKER; marker < AFTER_LAST_SKIPPABLE_MARKER; marker++) {
            signature[0] = marker;
            assertTrue(ZstdUtils.matches(signature, SKIPPABLE_FRAME_LENGTH));
        }

        // Fewer than four bytes are never a match, even with a valid marker (0x5F, left over above).
        assertFalse(ZstdUtils.matches(signature, SKIPPABLE_FRAME_LENGTH - 1));

        // Extra available bytes still match: only the first four are inspected.
        assertTrue(ZstdUtils.matches(signature, SKIPPABLE_FRAME_LENGTH + 1));
    }
}
