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

public class BinaryCodecTest_testDecodeByteArray {

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

    /*
     * Tests for byte[] decode(byte[])
     */
    @Test
    void testDecodeByteArray() {
        // With a single raw binary
        byte[] bits = new byte[1];
        byte[] decoded = instance.decode("00000000".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0;
        decoded = instance.decode("00000001".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1;
        decoded = instance.decode("00000011".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2;
        decoded = instance.decode("00000111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        decoded = instance.decode("00001111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        decoded = instance.decode("00011111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        decoded = instance.decode("00111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        decoded = instance.decode("01111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[1];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("11111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        // With a two raw binaries
        bits = new byte[2];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0000000011111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0000000111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0000001111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0000011111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0000111111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0001111111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0011111111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("0111111111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
        bits = new byte[2];
        bits[1] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = instance.decode("1111111111111111".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
    }
}
