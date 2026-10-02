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

public class Base58Test_testIsInAlphabet {

    private static final int BOUND = 10_000;

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static void assertArrayEqualsAt(final byte[] data, final byte[] dec, final int i) {
        final AtomicInteger counter = new AtomicInteger(i);
        assertArrayEquals(data, dec, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(data)));
    }

    private final Random random = new Random();

    @Test
    void testIsInAlphabet() {
        final Base58 base58 = new Base58();
        // Valid characters
        for (char c = '1'; c <= '9'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'A'; c <= 'H'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'J'; c <= 'N'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'P'; c <= 'Z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'a'; c <= 'k'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        for (char c = 'm'; c <= 'z'; c++) {
            assertTrue(base58.isInAlphabet((byte) c), "char " + c);
        }
        // Invalid characters - excluded from Base58
        assertFalse(base58.isInAlphabet((byte) '0'), "char 0");
        assertFalse(base58.isInAlphabet((byte) 'O'), "char O");
        assertFalse(base58.isInAlphabet((byte) 'I'), "char I");
        assertFalse(base58.isInAlphabet((byte) 'l'), "char l");
        // Out of bounds
        assertFalse(base58.isInAlphabet((byte) -1));
        assertFalse(base58.isInAlphabet((byte) 0));
        assertFalse(base58.isInAlphabet((byte) 128));
        assertFalse(base58.isInAlphabet((byte) 255));
    }
}
