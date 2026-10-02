package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ZstdUtilsTest_testMatchesZstandardFrame {

    @Test
    void testMatchesZstandardFrame() {
        final byte[] data = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD };
        assertFalse(ZstdUtils.matches(data, 3));
        assertTrue(ZstdUtils.matches(data, 4));
        assertTrue(ZstdUtils.matches(data, 5));
        data[3] = '0';
        assertFalse(ZstdUtils.matches(data, 4));
    }
}
