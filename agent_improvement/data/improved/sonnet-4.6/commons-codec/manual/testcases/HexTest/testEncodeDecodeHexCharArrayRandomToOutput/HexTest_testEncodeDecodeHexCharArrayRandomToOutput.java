package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeDecodeHexCharArrayRandomToOutput {

    @Test
    void testEncodeDecodeHexCharArrayRandomToOutput() throws DecoderException {
        for (int i = 0; i < 5; i++) {
            final byte[] data = new byte[ThreadLocalRandom.current().nextInt(10000) + 1];
            ThreadLocalRandom.current().nextBytes(data);

            // Verify round-trip with lower-case hex encoding written into a caller-supplied output buffer
            final char[] lowerEncodedChars = new char[data.length * 2];
            Hex.encodeHex(data, 0, data.length, true, lowerEncodedChars, 0);
            final byte[] decodedLowerCaseBytes = Hex.decodeHex(lowerEncodedChars);
            assertArrayEquals(data, decodedLowerCaseBytes);

            // Verify round-trip with upper-case hex encoding written into a caller-supplied output buffer
            final char[] upperEncodedChars = new char[data.length * 2];
            Hex.encodeHex(data, 0, data.length, false, upperEncodedChars, 0);
            final byte[] decodedUpperCaseBytes = Hex.decodeHex(upperEncodedChars);
            assertArrayEquals(data, decodedUpperCaseBytes);
        }
    }
}
