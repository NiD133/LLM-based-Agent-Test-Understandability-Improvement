package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesZstandardFrame {

    private static final int ZSTANDARD_FRAME_MAGIC_LENGTH = 4;
    private static final int LENGTH_SHORTER_THAN_MAGIC = ZSTANDARD_FRAME_MAGIC_LENGTH - 1;
    private static final int LENGTH_LONGER_THAN_MAGIC = ZSTANDARD_FRAME_MAGIC_LENGTH + 1;

    @Test
    void testMatchesZstandardFrame() {
        final byte[] zstandardFrameMagic = {
            (byte) 0x28,
            (byte) 0xB5,
            (byte) 0x2F,
            (byte) 0xFD
        };

        assertFalse(ZstdUtils.matches(zstandardFrameMagic, LENGTH_SHORTER_THAN_MAGIC));
        assertTrue(ZstdUtils.matches(zstandardFrameMagic, ZSTANDARD_FRAME_MAGIC_LENGTH));
        assertTrue(ZstdUtils.matches(zstandardFrameMagic, LENGTH_LONGER_THAN_MAGIC));

        zstandardFrameMagic[3] = '0';
        assertFalse(ZstdUtils.matches(zstandardFrameMagic, ZSTANDARD_FRAME_MAGIC_LENGTH));
    }
}
