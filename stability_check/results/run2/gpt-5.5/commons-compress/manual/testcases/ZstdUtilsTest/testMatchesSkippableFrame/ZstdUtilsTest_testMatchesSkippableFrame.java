package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    private static final byte SKIPPABLE_FRAME_MIN_MAGIC_PREFIX = (byte) 0x50;
    private static final byte SKIPPABLE_FRAME_MAX_MAGIC_PREFIX_EXCLUSIVE = (byte) 0x60;

    @Test
    void testMatchesSkippableFrame() {
        final byte[] skippableFrameSignature = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

        assertFalse(ZstdUtils.matches(skippableFrameSignature, 4));
        for (byte magicPrefix = SKIPPABLE_FRAME_MIN_MAGIC_PREFIX; magicPrefix < SKIPPABLE_FRAME_MAX_MAGIC_PREFIX_EXCLUSIVE; magicPrefix++) {
            skippableFrameSignature[0] = magicPrefix;
            assertTrue(ZstdUtils.matches(skippableFrameSignature, 4));
        }
        assertFalse(ZstdUtils.matches(skippableFrameSignature, 3));
        assertTrue(ZstdUtils.matches(skippableFrameSignature, 5));
    }
}
