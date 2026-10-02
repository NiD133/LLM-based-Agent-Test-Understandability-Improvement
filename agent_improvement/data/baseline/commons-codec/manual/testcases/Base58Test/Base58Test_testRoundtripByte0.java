package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayFill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Base58Test_testRoundtripByte0 {

    private static final int BOUND = 10_000;

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static void assertArrayEqualsAt(final byte[] data, final byte[] dec, final int i) {
        final AtomicInteger counter = new AtomicInteger(i);
        assertArrayEquals(data, dec, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(data)));
    }

    private final Random random = new Random();

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    void testRoundtripByte0(final int len) throws IOException {
        // Sanity check, each step from scratch:
        final byte[] zeros = new byte[len];
        final byte[] encoded0s = ArrayFill.fill(zeros.clone(), (byte) '1');
        assertArrayEquals(encoded0s, Base58.builder().get().encode(zeros));
        final byte[] decoded = Base58.builder().get().decode(encoded0s);
        assertArrayEquals(zeros, decoded, () -> String.format("zeros=%s, decoded=%s", Arrays.toString(zeros), Arrays.toString(decoded)));
    }
}
