package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    private static final byte SKIPPABLE_FRAME_PREFIX_START = (byte) 0x50;
    private static final byte SKIPPABLE_FRAME_PREFIX_END_EXCLUSIVE = (byte) 0x60;
    private static final byte SKIPPABLE_FRAME_COMMON_BYTE_1 = (byte) 0x2A;
    private static final byte SKIPPABLE_FRAME_COMMON_BYTE_2 = (byte) 0x4D;
    private static final byte SKIPPABLE_FRAME_COMMON_BYTE_3 = (byte) 0x18;

    @Test
    void testMatchesSkippableFrame() {
        final byte[] skippableFrameSignature = {
            0,
            SKIPPABLE_FRAME_COMMON_BYTE_1,
            SKIPPABLE_FRAME_COMMON_BYTE_2,
            SKIPPABLE_FRAME_COMMON_BYTE_3
        };

        assertFalse(ZstdUtils.matches(skippableFrameSignature, 4));

        for (byte prefix = SKIPPABLE_FRAME_PREFIX_START; prefix < SKIPPABLE_FRAME_PREFIX_END_EXCLUSIVE; prefix++) {
            skippableFrameSignature[0] = prefix;
            assertTrue(ZstdUtils.matches(skippableFrameSignature, 4));
        }

        assertFalse(ZstdUtils.matches(skippableFrameSignature, 3));
        assertTrue(ZstdUtils.matches(skippableFrameSignature, 5));
    }
}
