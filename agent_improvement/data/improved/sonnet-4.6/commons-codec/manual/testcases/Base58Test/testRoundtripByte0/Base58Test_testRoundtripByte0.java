package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies the Base58 round-trip property for byte arrays that consist entirely of zero bytes.
 *
 * <p>In Base58 encoding, a leading zero byte maps to the character '1'. Therefore an array of
 * {@code n} zero bytes must encode to exactly {@code n} '1' characters, and decoding those
 * characters must restore the original zero bytes.
 */
public class Base58Test_testRoundtripByte0 {

    /**
     * Tests that encoding an all-zero byte array of the given length produces an all-'1' character
     * array of the same length, and that decoding those '1' characters restores the original zeros.
     *
     * @param len the number of zero bytes to encode/decode (0 through 4)
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    void testRoundtripByte0(final int len) throws IOException {
        // Input: an array of `len` zero bytes
        final byte[] zeros = new byte[len];

        // In Base58, each leading 0x00 byte encodes to the '1' character.
        // So `len` zeros must encode to exactly `len` '1' characters.
        final byte[] expectedEncoded = ArrayFill.fill(zeros.clone(), (byte) '1');
        assertArrayEquals(expectedEncoded, Base58.builder().get().encode(zeros));

        // Decoding those '1' characters must restore the original zero bytes.
        final byte[] decoded = Base58.builder().get().decode(expectedEncoded);
        assertArrayEquals(zeros, decoded,
                () -> String.format("zeros=%s, decoded=%s",
                        Arrays.toString(zeros), Arrays.toString(decoded)));
    }
}
