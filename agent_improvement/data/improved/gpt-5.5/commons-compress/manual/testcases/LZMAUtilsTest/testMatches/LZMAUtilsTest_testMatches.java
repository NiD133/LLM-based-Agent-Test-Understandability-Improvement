package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testMatches {

    private static final byte LZMA_PROPERTY_BYTE = (byte) 0x5D;
    private static final byte ZERO_DICTIONARY_SIZE_BYTE = 0;

    @Test
    void testMatches() {
        final byte[] lzmaHeader = { LZMA_PROPERTY_BYTE, ZERO_DICTIONARY_SIZE_BYTE, ZERO_DICTIONARY_SIZE_BYTE };

        assertFalse(LZMAUtils.matches(lzmaHeader, 2));
        assertTrue(LZMAUtils.matches(lzmaHeader, 3));
        assertTrue(LZMAUtils.matches(lzmaHeader, 4));

        lzmaHeader[2] = '0';

        assertFalse(LZMAUtils.matches(lzmaHeader, 3));
    }
}
