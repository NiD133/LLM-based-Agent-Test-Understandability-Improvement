package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToByteArrayFromString {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** Mask with bit zero-based index 0 raised. */
    private static final int BIT_0 = 0x01;

    /** Mask with bit zero-based index 1 raised. */
    private static final int BIT_1 = 0x02;

    /** Mask with bit zero-based index 2 raised. */
    private static final int BIT_2 = 0x04;

    /** Mask with bit zero-based index 3 raised. */
    private static final int BIT_3 = 0x08;

    /** Mask with bit zero-based index 4 raised. */
    private static final int BIT_4 = 0x10;

    /** Mask with bit zero-based index 5 raised. */
    private static final int BIT_5 = 0x20;

    /** Mask with bit zero-based index 6 raised. */
    private static final int BIT_6 = 0x40;

    /** Mask with bit zero-based index 7 raised. */
    private static final int BIT_7 = 0x80;

    BinaryCodec instance;

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

    /**
     * Decodes {@code binaryString} and asserts the result equals {@code expectedBits}.
     */
    private void assertToByteArray(byte[] expectedBits, String binaryString) {
        byte[] decoded = instance.toByteArray(binaryString);
        assertEquals(new String(expectedBits), new String(decoded));
    }

    /**
     * Tests for byte[] toByteArray(String)
     */
    @Test
    void testToByteArrayFromString() {
        // Single byte: progressively set bits from LSB (bit 0) to MSB (bit 7)
        assertToByteArray(new byte[]{0}, "00000000");
        assertToByteArray(new byte[]{(byte) BIT_0}, "00000001");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1)}, "00000011");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2)}, "00000111");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)}, "00001111");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)}, "00011111");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)}, "00111111");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)}, "01111111");
        assertToByteArray(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7)}, "11111111");

        // Two bytes: lower byte (index 0) is all ones; upper byte (index 1) gains bits progressively
        byte allBitsSet = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        assertToByteArray(new byte[]{allBitsSet, 0}, "0000000011111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) BIT_0}, "0000000111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1)}, "0000001111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2)}, "0000011111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)}, "0000111111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)}, "0001111111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)}, "0011111111111111");
        assertToByteArray(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)}, "0111111111111111");
        assertToByteArray(new byte[]{allBitsSet, allBitsSet}, "1111111111111111");

        // Null input returns an empty byte array
        assertEquals(0, instance.toByteArray((String) null).length);
    }
}
