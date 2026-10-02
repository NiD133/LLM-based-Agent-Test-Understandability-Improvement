package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    private static final int FULL_MAGIC_LENGTH = 4;
    private static final int TOO_SHORT_LENGTH = 3;
    private static final int LONGER_THAN_MAGIC_LENGTH = 5;

    @Test
    void testMatchesSkippableFrame() {
        final byte[] skippableFrameMagic = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };

        assertFalse(ZstdUtils.matches(skippableFrameMagic, FULL_MAGIC_LENGTH));

        for (byte skippableFramePrefix = (byte) 0x50; skippableFramePrefix < 0x60; skippableFramePrefix++) {
            skippableFrameMagic[0] = skippableFramePrefix;
            assertTrue(ZstdUtils.matches(skippableFrameMagic, FULL_MAGIC_LENGTH));
        }

        assertFalse(ZstdUtils.matches(skippableFrameMagic, TOO_SHORT_LENGTH));
        assertTrue(ZstdUtils.matches(skippableFrameMagic, LONGER_THAN_MAGIC_LENGTH));
    }
}
