package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ZstdUtils#matches(byte[], int)} for Zstandard "skippable frame" signatures.
 *
 * <p>
 * A skippable frame is identified by a 4-byte magic number where:
 * </p>
 * <ul>
 *   <li>the first byte has its high nibble equal to {@code 0x5} (i.e. any value in the range {@code 0x50}-{@code 0x5F}), and</li>
 *   <li>the remaining three bytes are exactly {@code 0x2A 0x4D 0x18}.</li>
 * </ul>
 */
public class ZstdUtilsTest_testMatchesSkippableFrame {

    /** The three fixed magic bytes that follow the variable first byte of a skippable frame. */
    private static final byte[] SKIPPABLE_FRAME_MAGIC_TAIL = { (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

    /** First byte values in the range [0x50, 0x60) all mark a skippable frame. */
    private static final byte FIRST_SKIPPABLE_BYTE = (byte) 0x50;
    private static final byte FIRST_NON_SKIPPABLE_BYTE = (byte) 0x60;

    @Test
    void testMatchesSkippableFrame() {
        // signature = { firstByte, 0x2A, 0x4D, 0x18 }; the first byte is updated below.
        final byte[] signature = { 0, SKIPPABLE_FRAME_MAGIC_TAIL[0], SKIPPABLE_FRAME_MAGIC_TAIL[1], SKIPPABLE_FRAME_MAGIC_TAIL[2] };

        // A leading byte of 0x00 has the wrong high nibble, so this is not a skippable frame.
        assertFalse(ZstdUtils.matches(signature, 4));

        // Every leading byte in [0x50, 0x60) yields a valid skippable frame.
        for (byte firstByte = FIRST_SKIPPABLE_BYTE; firstByte < FIRST_NON_SKIPPABLE_BYTE; firstByte++) {
            signature[0] = firstByte;
            assertTrue(ZstdUtils.matches(signature, 4));
        }

        // A length below the 4-byte magic length never matches, even for a valid skippable signature.
        assertFalse(ZstdUtils.matches(signature, 3));

        // A length greater than the magic length still matches; only the first 4 bytes are inspected.
        assertTrue(ZstdUtils.matches(signature, 5));
    }
}
