package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesSkippableFrame {

    @Test
    void testMatchesSkippableFrame() {
        final byte[] data = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18 };
        assertFalse(ZstdUtils.matches(data, 4));
        for (byte b = (byte) 0x50; b < 0x60; b++) {
            data[0] = b;
            assertTrue(ZstdUtils.matches(data, 4));
        }
        assertFalse(ZstdUtils.matches(data, 3));
        assertTrue(ZstdUtils.matches(data, 5));
    }
}
