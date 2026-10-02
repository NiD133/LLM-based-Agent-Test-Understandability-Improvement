package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies that an all-zero byte array survives a Base58 encode/decode round trip.
 *
 * <p>In Base58 each leading zero byte is represented by a single {@code '1'}
 * character (the first symbol of the alphabet). So an input of {@code N} zero
 * bytes must encode to {@code N} {@code '1'} characters, and decoding those
 * characters must reproduce the original {@code N} zero bytes.</p>
 */
public class Base58Test_testRoundtripByte0 {

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    void testRoundtripByte0(final int numberOfZeroBytes) throws IOException {
        // Input: a run of zero bytes of the given length.
        final byte[] zeroBytes = new byte[numberOfZeroBytes];

        // Expected encoding: one '1' character per leading zero byte.
        final byte[] expectedEncoding = ArrayFill.fill(zeroBytes.clone(), (byte) '1');

        // Encoding the zero bytes must produce the run of '1' characters.
        final byte[] actualEncoding = Base58.builder().get().encode(zeroBytes);
        assertArrayEquals(expectedEncoding, actualEncoding);

        // Decoding that run of '1' characters must reproduce the original zero bytes.
        final byte[] decoded = Base58.builder().get().decode(expectedEncoding);
        assertArrayEquals(zeroBytes, decoded,
                () -> String.format("zeros=%s, decoded=%s",
                        Arrays.toString(zeroBytes), Arrays.toString(decoded)));
    }
}
