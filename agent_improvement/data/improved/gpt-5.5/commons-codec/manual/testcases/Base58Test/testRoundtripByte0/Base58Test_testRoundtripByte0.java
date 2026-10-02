package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Base58Test_testRoundtripByte0 {

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    void testRoundtripByte0(final int len) throws IOException {
        final byte[] zeroBytes = new byte[len];
        final byte[] expectedEncoding = ArrayFill.fill(zeroBytes.clone(), (byte) '1');

        assertArrayEquals(expectedEncoding, Base58.builder().get().encode(zeroBytes));

        final byte[] decodedBytes = Base58.builder().get().decode(expectedEncoding);
        assertArrayEquals(zeroBytes, decodedBytes,
                () -> String.format("zeros=%s, decoded=%s", Arrays.toString(zeroBytes), Arrays.toString(decodedBytes)));
    }
}
