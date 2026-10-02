package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeDecodeHexCharArrayRandomToOutput {

    private static final int RANDOM_SAMPLE_COUNT = 5;
    private static final int MAX_RANDOM_BYTE_COUNT_EXCLUSIVE = 10000;

    @Test
    void testEncodeDecodeHexCharArrayRandomToOutput() throws DecoderException {
        for (int remainingSamples = RANDOM_SAMPLE_COUNT; remainingSamples > 0; remainingSamples--) {
            final byte[] data = new byte[ThreadLocalRandom.current().nextInt(MAX_RANDOM_BYTE_COUNT_EXCLUSIVE) + 1];
            ThreadLocalRandom.current().nextBytes(data);

            assertEncodeDecodeRoundTrip(data, true);
            assertEncodeDecodeRoundTrip(data, false);
        }
    }

    private void assertEncodeDecodeRoundTrip(final byte[] data, final boolean toLowerCase) throws DecoderException {
        final char[] encodedChars = new char[data.length * 2];

        Hex.encodeHex(data, 0, data.length, toLowerCase, encodedChars, 0);
        final byte[] decodedBytes = Hex.decodeHex(encodedChars);

        assertArrayEquals(data, decodedBytes);
    }
}
