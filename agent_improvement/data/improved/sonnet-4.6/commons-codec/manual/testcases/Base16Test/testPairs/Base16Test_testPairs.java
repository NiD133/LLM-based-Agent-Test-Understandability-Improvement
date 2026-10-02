package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_testPairs {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private final Random random = new Random();

    Random getRandom() {
        return this.random;
    }

    private void testBase16InBuffer(final int startPasSize, final int endPadSize) {
        final String content = "Hello World";
        final String encodedContent;
        final byte[] bytesUtf8 = StringUtils.getBytesUtf8(content);
        byte[] buffer = ArrayUtils.addAll(bytesUtf8, new byte[endPadSize]);
        buffer = ArrayUtils.addAll(new byte[startPasSize], buffer);
        final byte[] encodedBytes = new Base16().encode(buffer, startPasSize, bytesUtf8.length);
        encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals("48656C6C6F20576F726C64", encodedContent, "encoding hello world");
    }

    private String toString(final byte[] data) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i != data.length - 1) {
                buf.append(",");
            }
        }
        return buf.toString();
    }

    /** Asserts that encoding the two-byte array {first, second} produces expectedHex. */
    private void assertEncodesTo(final Base16 codec, final byte first, final byte second, final String expectedHex) {
        assertEquals(expectedHex, new String(codec.encode(new byte[] { first, second })));
    }

    @Test
    void testPairs() {
        final Base16 codec = new Base16();

        // Verify expected hex encodings for two-byte inputs where the first byte is 0x00
        // and the second byte ranges from 0x00 (0) through 0x11 (17).
        assertEncodesTo(codec, (byte) 0, (byte)  0, "0000");
        assertEncodesTo(codec, (byte) 0, (byte)  1, "0001");
        assertEncodesTo(codec, (byte) 0, (byte)  2, "0002");
        assertEncodesTo(codec, (byte) 0, (byte)  3, "0003");
        assertEncodesTo(codec, (byte) 0, (byte)  4, "0004");
        assertEncodesTo(codec, (byte) 0, (byte)  5, "0005");
        assertEncodesTo(codec, (byte) 0, (byte)  6, "0006");
        assertEncodesTo(codec, (byte) 0, (byte)  7, "0007");
        assertEncodesTo(codec, (byte) 0, (byte)  8, "0008");
        assertEncodesTo(codec, (byte) 0, (byte)  9, "0009");
        assertEncodesTo(codec, (byte) 0, (byte) 10, "000A");
        assertEncodesTo(codec, (byte) 0, (byte) 11, "000B");
        assertEncodesTo(codec, (byte) 0, (byte) 12, "000C");
        assertEncodesTo(codec, (byte) 0, (byte) 13, "000D");
        assertEncodesTo(codec, (byte) 0, (byte) 14, "000E");
        assertEncodesTo(codec, (byte) 0, (byte) 15, "000F");
        assertEncodesTo(codec, (byte) 0, (byte) 16, "0010");
        assertEncodesTo(codec, (byte) 0, (byte) 17, "0011");

        // Verify that encode followed by decode is a round-trip identity for all byte values.
        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i, (byte) i };
            assertArrayEquals(test, codec.decode(codec.encode(test)));
        }
    }
}
