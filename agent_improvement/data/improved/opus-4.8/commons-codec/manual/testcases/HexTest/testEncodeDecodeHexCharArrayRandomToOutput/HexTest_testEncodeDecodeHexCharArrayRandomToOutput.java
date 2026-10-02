package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeDecodeHexCharArrayRandomToOutput {

    private static final int RANDOM_TRIALS = 5;
    private static final int MAX_RANDOM_DATA_LENGTH = 10000;

    /**
     * Encodes random byte arrays to hex characters using the offset-based
     * {@link Hex#encodeHex(byte[], int, int, boolean, char[], int)} overload (which writes into a
     * caller-supplied char[]), then decodes them back and verifies the round-trip reproduces the
     * original bytes. Both the lower-case and upper-case alphabets are exercised.
     */
    @Test
    void testEncodeDecodeHexCharArrayRandomToOutput() throws DecoderException {
        for (int trial = 0; trial < RANDOM_TRIALS; trial++) {
            // Generate a random, non-empty byte array (length 1..MAX_RANDOM_DATA_LENGTH).
            final byte[] data = new byte[ThreadLocalRandom.current().nextInt(MAX_RANDOM_DATA_LENGTH) + 1];
            ThreadLocalRandom.current().nextBytes(data);

            // Each byte encodes to two hex characters.
            final int encodedLength = data.length * 2;

            // Lower-case round-trip.
            final char[] lowerEncodedChars = new char[encodedLength];
            Hex.encodeHex(data, 0, data.length, true, lowerEncodedChars, 0);
            assertArrayEquals(data, Hex.decodeHex(lowerEncodedChars));

            // Upper-case round-trip.
            final char[] upperEncodedChars = new char[encodedLength];
            Hex.encodeHex(data, 0, data.length, false, upperEncodedChars, 0);
            assertArrayEquals(data, Hex.decodeHex(upperEncodedChars));
        }
    }
}
