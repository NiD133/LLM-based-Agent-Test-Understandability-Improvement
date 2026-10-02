package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding random byte data to hexadecimal and decoding it back
 * yields the original data, exercising every public {@link Hex} encode/decode
 * entry point: the static char[] API, the instance byte[] API, and the generic
 * {@code Object}-based API for both {@code char[]} and {@code String} inputs.
 */
public class HexTest_testEncodeDecodeHexCharArrayRandom {

    /** Number of random round-trips to perform. */
    private static final int RANDOM_TRIALS = 5;

    /** Upper bound (exclusive) on the random size of each data buffer. */
    private static final int MAX_DATA_SIZE = 10000;

    @Test
    void testEncodeDecodeHexCharArrayRandom() throws DecoderException, EncoderException {
        final Hex hex = new Hex();

        for (int trial = 0; trial < RANDOM_TRIALS; trial++) {
            // Generate a non-empty byte buffer of random length filled with random bytes.
            final byte[] data = new byte[ThreadLocalRandom.current().nextInt(MAX_DATA_SIZE) + 1];
            ThreadLocalRandom.current().nextBytes(data);

            // 1. Static char[] API: encodeHex / decodeHex round-trip.
            final char[] encodedChars = Hex.encodeHex(data);
            assertArrayEquals(data, Hex.decodeHex(encodedChars));

            // 2. Instance byte[] API: encode / decode round-trip.
            final byte[] encodedBytes = hex.encode(data);
            assertArrayEquals(data, hex.decode(encodedBytes));

            // The hex string is the textual form of the encoded characters; its
            // UTF-8 bytes are the expected result of the Object-based decode calls below.
            final String hexString = new String(encodedChars);
            final byte[] expectedDecodedBytes = StringUtils.getBytesUtf8(hexString);

            // 3. Generic Object API with a String input encoded to char[],
            //    then decoded from that char[].
            final char[] encodedFromString = (char[]) hex.encode(hexString);
            assertArrayEquals(expectedDecodedBytes, (byte[]) hex.decode(encodedFromString));

            // 4. Generic Object API again, but decode from a String wrapping the char[].
            final char[] encodedFromStringAgain = (char[]) hex.encode(hexString);
            assertArrayEquals(expectedDecodedBytes, (byte[]) hex.decode(new String(encodedFromStringAgain)));
        }
    }
}
