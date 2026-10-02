package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeObject {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    // Bit masks for each of the 8 bit positions in a byte (bit 0 = LSB, bit 7 = MSB)
    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    // Convenience constant for a byte with all 8 bits set (0xFF)
    private static final byte ALL_BITS =
            (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    private BinaryCodec instance;

    /**
     * Builds a byte array from the given integer values, casting each element to a byte.
     * This eliminates the repetitive {@code bits = new byte[N]; bits[i] = ...} pattern
     * and keeps test cases concise.
     */
    private static byte[] buildBytes(final int... values) {
        final byte[] result = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (byte) values[i];
        }
        return result;
    }

    /**
     * Verifies that decoding the ASCII binary string {@code encodeMe} produces the raw binary
     * bytes in {@code expectedBits}, exercising all three supported input types:
     * <ol>
     *   <li>String  — via {@code decode(Object)}</li>
     *   <li>byte[]  — via {@code decode(byte[])} or {@code decode(Object)}</li>
     *   <li>char[]  — via {@code decode(Object)}</li>
     * </ol>
     *
     * @param expectedBits the expected raw binary bytes after decoding
     * @param encodeMe     the ASCII binary string to decode (e.g. {@code "01001010"}),
     *                     or {@code null} to verify that null/empty inputs yield an empty array
     */
    private void assertDecodeObject(final byte[] expectedBits, final String encodeMe) throws DecoderException {
        // 1. Decode the String via the Object overload
        byte[] decoded = (byte[]) instance.decode(encodeMe);
        assertArrayEquals(expectedBits, decoded, "decode(String) produced unexpected bytes");

        // 2. Decode via byte[] (null input uses the typed overload; non-null wraps in Object cast)
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertArrayEquals(expectedBits, decoded, "decode(byte[]) produced unexpected bytes");

        // 3. Decode via char[] (always through the Object overload)
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertArrayEquals(expectedBits, decoded, "decode(char[]) produced unexpected bytes");
    }

    @BeforeEach
    void setUp() {
        instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    /**
     * Tests {@link BinaryCodec#decode(Object)} across a range of ASCII binary strings.
     *
     * <p>Single-byte cases: bits are activated one at a time from LSB (bit 0) to MSB (bit 7),
     * so each successive string has one more '1' on the right.
     *
     * <p>Two-byte cases: the low byte (index 0) always has all bits set; the high byte
     * (index 1) gains bits incrementally from LSB to MSB, so the 16-character string
     * gains a leading '1' each time.
     *
     * <p>Null case: a null input must yield an empty byte array.
     */
    @Test
    void testDecodeObject() throws Exception {
        // --- Single-byte decoding: each step sets one additional bit (LSB to MSB) ---
        assertDecodeObject(buildBytes(0x00),                                                          "00000000");
        assertDecodeObject(buildBytes(BIT_0),                                                         "00000001");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1),                                                 "00000011");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1 | BIT_2),                                         "00000111");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3),                                "00001111");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                       "00011111");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),              "00111111");
        assertDecodeObject(buildBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),     "01111111");
        assertDecodeObject(buildBytes(ALL_BITS),                                                      "11111111");

        // --- Two-byte decoding: index-0 byte is always ALL_BITS; index-1 byte gains bits LSB→MSB ---
        assertDecodeObject(buildBytes(ALL_BITS, 0x00),                                                "0000000011111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0),                                               "0000000111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1),                                       "0000001111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1 | BIT_2),                               "0000011111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3),                      "0000111111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),             "0001111111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),    "0011111111111111");
        assertDecodeObject(buildBytes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6), "0111111111111111");
        assertDecodeObject(buildBytes(ALL_BITS, ALL_BITS),                                            "1111111111111111");

        // --- Null input: decoding null must produce an empty byte array ---
        assertDecodeObject(new byte[0], null);
    }
}
