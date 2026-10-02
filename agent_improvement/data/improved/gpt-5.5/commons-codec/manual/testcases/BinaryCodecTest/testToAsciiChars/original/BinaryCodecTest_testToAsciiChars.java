package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToAsciiChars {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * Mask with bit zero-based index 0 raised.
     */
    private static final int BIT_0 = 0x01;

    /**
     * Mask with bit zero-based index 1 raised.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask with bit zero-based index 2 raised.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask with bit zero-based index 3 raised.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask with bit zero-based index 4 raised.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask with bit zero-based index 5 raised.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask with bit zero-based index 6 raised.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask with bit zero-based index 7 raised.
     */
    private static final int BIT_7 = 0x80;

    /**
     * An instance of the binary codec.
     */
    BinaryCodec instance;

    /**
     * Utility used to assert the encoded and decoded values.
     *
     * @param bits
     *            the pre-encoded data
     * @param encodeMe
     *            data to encode and compare
     */
    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded;
        decoded = (byte[]) instance.decode(encodeMe);
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertEquals(new String(bits), new String(decoded));
    }

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testToAsciiChars() {
        // With a single raw binary
        byte[] bits = new byte[1];
        String encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00000000", encoded);
        bits = new byte[1];
        bits[0] = BIT_0;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00000001", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00000011", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00000111", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00001111", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00011111", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("00111111", encoded);
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("01111111", encoded);
        bits = new byte[1];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("11111111", encoded);
        // With a two raw binaries
        bits = new byte[2];
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000000000", encoded);
        bits = new byte[2];
        bits[0] = BIT_0;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000000001", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000000011", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1 | BIT_2;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000000111", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000001111", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000011111", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000000111111", encoded);
        bits = new byte[2];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000001111111", encoded);
        bits = new byte[2];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000011111111", encoded);
        // work on the other byte now
        bits = new byte[2];
        bits[1] = BIT_0;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000000111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000001111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000011111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0000111111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0001111111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0011111111111111", encoded);
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("0111111111111111", encoded);
        bits = new byte[2];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        bits[1] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        encoded = new String(BinaryCodec.toAsciiChars(bits));
        assertEquals("1111111111111111", encoded);
        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }
}
