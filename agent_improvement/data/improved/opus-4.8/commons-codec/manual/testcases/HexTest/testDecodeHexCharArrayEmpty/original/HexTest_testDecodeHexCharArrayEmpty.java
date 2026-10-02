package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HexTest_testDecodeHexCharArrayEmpty {

    private static final String BAD_ENCODING_NAME = "UNKNOWN";

    private static final boolean LOG = false;

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    private boolean charsetSanityCheck(final String name) {
        final String source = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = source.getBytes(name);
            final String str = new String(bytes, name);
            final boolean equals = source.equals(str);
            if (!equals) {
                // Here with:
                //
                // Java Sun 1.4.2_19 x86 32-bits on Windows XP
                // JIS_X0212-1990
                // x-JIS0208
                //
                // Java Sun 1.5.0_17 x86 32-bits on Windows XP
                // JIS_X0212-1990
                // x-IBM834
                // x-JIS0208
                // x-MacDingbat
                // x-MacSymbol
                //
                // Java Sun 1.6.0_14 x86 32-bits
                // JIS_X0212-1990
                // x-IBM834
                // x-JIS0208
                // x-MacDingbat
                // x-MacSymbol
                //
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + name);
            }
            return equals;
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            // Caught here with:
            // x-JISAutoDetect on Windows XP and Java Sun 1.4.2_19 x86 32-bits
            // x-JISAutoDetect on Windows XP and Java Sun 1.5.0_17 x86 32-bits
            // x-JISAutoDetect on Windows XP and Java Sun 1.6.0_14 x86 32-bits
            if (LOG) {
                log("FAILED charsetSanityCheck=" + name + ", e=" + e);
                log(e);
            }
            return false;
        }
    }

    private void checkDecodeHexByteBufferOddCharacters(final ByteBuffer data) {
        assertThrows(DecoderException.class, () -> new Hex().decode(data));
    }

    private void checkDecodeHexCharArrayOddCharacters(final char[] data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    private void checkDecodeHexCharArrayOddCharacters(final String data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    /**
     * Encodes the given string into a byte buffer using the UTF-8 charset.
     *
     * <p>The buffer is allocated using {@link #allocate(int)}.
     *
     * @param string the String to encode
     * @return the byte buffer
     */
    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    private void log(final String s) {
        if (LOG) {
            System.out.println(s);
            System.out.flush();
        }
    }

    private void log(final Throwable t) {
        if (LOG) {
            t.printStackTrace(System.out);
            System.out.flush();
        }
    }

    /**
     * @param name
     * @param parent
     * @throws UnsupportedEncodingException
     * @throws DecoderException
     */
    private void testCharset(final String name, final String parent) throws UnsupportedEncodingException, DecoderException {
        if (!charsetSanityCheck(name)) {
            return;
        }
        log(parent + "=" + name);
        final Hex customCodec = new Hex(name);
        // source data
        final String sourceString = "Hello World";
        final byte[] sourceBytes = sourceString.getBytes(name);
        // test 1
        // encode source to hex string to bytes with charset
        final byte[] actualEncodedBytes = customCodec.encode(sourceBytes);
        // encode source to hex string...
        String expectedHexString = Hex.encodeHexString(sourceBytes);
        // ... and get the bytes in the expected charset
        final byte[] expectedHexStringBytes = expectedHexString.getBytes(name);
        assertArrayEquals(expectedHexStringBytes, actualEncodedBytes);
        // test 2
        String actualStringFromBytes = new String(actualEncodedBytes, name);
        assertEquals(expectedHexString, actualStringFromBytes, name);
        // second test:
        final Hex utf8Codec = new Hex();
        expectedHexString = "48656c6c6f20576f726c64";
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode(expectedHexString);
        actualStringFromBytes = new String(decodedUtf8Bytes, utf8Codec.getCharset());
        // sanity check:
        assertEquals(sourceString, actualStringFromBytes, name);
        // actual check:
        final byte[] decodedCustomBytes = customCodec.decode(actualEncodedBytes);
        actualStringFromBytes = new String(decodedCustomBytes, name);
        assertEquals(sourceString, actualStringFromBytes, name);
    }

    @Test
    void testDecodeHexCharArrayEmpty() throws DecoderException {
        assertArrayEquals(new byte[0], Hex.decodeHex(new char[0]));
    }
}
