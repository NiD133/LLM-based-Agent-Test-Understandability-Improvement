package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#decodeHex(char[], byte[], int)} when the supplied output
 * buffer cannot hold the decoded bytes because the write offset shrinks the
 * available space below what is required.
 */
public class HexTest_testDecodeHexCharArrayOutBufferUnderSizedByOffset {

    @Test
    void testDecodeHexCharArrayOutBufferUnderSizedByOffset() {
        // "aabbccddeeff" is 12 hex characters, which decode to 6 bytes.
        final char[] hexInput = "aabbccddeeff".toCharArray();

        // The output buffer holds exactly 6 bytes, but writing starts at
        // offset 1, leaving only 5 usable bytes - one short of the 6 needed.
        final byte[] outputBuffer = new byte[6];
        final int writeOffset = 1;

        // Decoding must fail because the output buffer is undersized by the offset.
        assertThrows(DecoderException.class,
                () -> Hex.decodeHex(hexInput, outputBuffer, writeOffset));
    }
}
