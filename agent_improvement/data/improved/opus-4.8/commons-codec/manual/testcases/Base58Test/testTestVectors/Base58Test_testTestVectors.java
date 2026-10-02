package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Base58} encoding and decoding against well-known reference vectors,
 * confirming both the exact encoded text and a successful encode-then-decode round trip.
 */
public class Base58Test_testTestVectors {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    @Test
    void testTestVectors() {
        // Three reference inputs paired with their expected Base58 encodings.
        // Vector 1: a short ASCII string.
        final String helloWorld = "Hello World!";
        final byte[] helloWorldBytes = helloWorld.getBytes(UTF_8);
        // Vector 2: a longer ASCII string.
        final String quickBrownFox = "The quick brown fox jumps over the lazy dog.";
        final byte[] quickBrownFoxBytes = quickBrownFox.getBytes(UTF_8);
        // Vector 3: the 48-bit number 0x0000287fb4cd as its 6 raw big-endian bytes.
        //           The two leading zero bytes exercise Base58's leading-zero handling.
        final byte[] numberBytes = trailingBytes(0x0000287fb4cdL, 6);

        // --- Encoding: each input must produce its known Base58 string. ---
        final byte[] encodedHelloWorld = new Base58().encode(helloWorldBytes);
        final byte[] encodedQuickBrownFox = new Base58().encode(quickBrownFoxBytes);
        final byte[] encodedNumber = new Base58().encode(numberBytes);

        assertEquals("2NEpo7TZRRrLZSi2U", new String(encodedHelloWorld, UTF_8), "encoding hello world");
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z",
                new String(encodedQuickBrownFox, UTF_8));
        assertEquals("11233QC4", new String(encodedNumber, UTF_8), "encoding 0x0000287fb4cd");

        // --- Decoding: feeding the encoded bytes back must restore the original input. ---
        final byte[] decodedHelloWorld = new Base58().decode(encodedHelloWorld);
        final byte[] decodedQuickBrownFox = new Base58().decode(encodedQuickBrownFox);
        final byte[] decodedNumber = new Base58().decode(encodedNumber);

        assertEquals(helloWorld, new String(decodedHelloWorld, UTF_8), "decoding hello world");
        assertEquals(quickBrownFox, new String(decodedQuickBrownFox, UTF_8));
        assertArrayEquals(numberBytes, decodedNumber, "decoding 0x0000287fb4cd");
    }

    /**
     * Returns the last {@code count} bytes of the big-endian, 8-byte representation of {@code value}.
     * For example, {@code trailingBytes(0x0000287fb4cdL, 6)} yields the 6 bytes
     * {@code 00 00 28 7f b4 cd}.
     */
    private static byte[] trailingBytes(final long value, final int count) {
        final byte[] eightBytes = ByteBuffer.allocate(Long.BYTES).putLong(value).array();
        final byte[] trailing = new byte[count];
        System.arraycopy(eightBytes, Long.BYTES - count, trailing, 0, count);
        return trailing;
    }
}
