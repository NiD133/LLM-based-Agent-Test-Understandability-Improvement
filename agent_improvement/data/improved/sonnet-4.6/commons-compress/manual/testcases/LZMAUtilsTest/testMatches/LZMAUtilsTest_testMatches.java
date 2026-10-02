package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testMatches {

    // LZMA files begin with a 3-byte magic header: 0x5D, 0x00, 0x00
    private static final int LZMA_MAGIC_LENGTH = 3;

    @Test
    void testMatches_insufficientLength_returnsFalse() {
        final byte[] validMagicBytes = { (byte) 0x5D, 0, 0 };
        assertFalse(LZMAUtils.matches(validMagicBytes, LZMA_MAGIC_LENGTH - 1));
    }

    @Test
    void testMatches_exactMagicLength_returnsTrue() {
        final byte[] validMagicBytes = { (byte) 0x5D, 0, 0 };
        assertTrue(LZMAUtils.matches(validMagicBytes, LZMA_MAGIC_LENGTH));
    }

    @Test
    void testMatches_moreThanMagicLength_returnsTrue() {
        final byte[] validMagicBytes = { (byte) 0x5D, 0, 0 };
        assertTrue(LZMAUtils.matches(validMagicBytes, LZMA_MAGIC_LENGTH + 1));
    }

    @Test
    void testMatches_corruptedThirdByte_returnsFalse() {
        final byte[] corruptedMagicBytes = { (byte) 0x5D, 0, '0' };
        assertFalse(LZMAUtils.matches(corruptedMagicBytes, LZMA_MAGIC_LENGTH));
    }
}
