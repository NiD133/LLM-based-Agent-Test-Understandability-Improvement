package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#decode(Object)} and its sibling overloads.
 *
 * <p>
 * {@code decode} takes a string of ASCII '0'/'1' characters and returns the raw bytes it encodes.
 * The codec is little-endian over bytes: the right-most group of 8 characters becomes byte 0, the
 * next group to the left becomes byte 1, and so on. Within a byte the right-most character is the
 * least-significant bit. Java's {@code 0b...} binary literals let each expected byte mirror the
 * matching 8 characters of the input string directly.
 * </p>
 */
public class BinaryCodecTest_testDecodeObject {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** The codec under test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    /**
     * Decodes {@code asciiBinary} through every {@code decode} overload and asserts that each one
     * yields {@code expectedRawBytes}.
     *
     * <p>
     * The same input is fed in as a {@link String}, as a {@code byte[]} (boxed via {@code Object})
     * and as a {@code char[]}, since all three paths must produce identical raw bytes. A
     * {@code null} input exercises the empty-result branch of each overload.
     * </p>
     *
     * @param expectedRawBytes the raw bytes the input is expected to decode to.
     * @param asciiBinary      the string of '0'/'1' characters to decode (may be {@code null}).
     */
    private void assertDecodesTo(final byte[] expectedRawBytes, final String asciiBinary) throws DecoderException {
        final String expected = new String(expectedRawBytes);

        // Path 1: decode(Object) dispatching on a String argument.
        final byte[] fromString = (byte[]) instance.decode(asciiBinary);
        assertEquals(expected, new String(fromString));

        // Path 2: decode(Object) dispatching on a byte[] argument (null routes through decode(byte[])).
        final byte[] fromBytes = asciiBinary == null
                ? instance.decode((byte[]) null)
                : (byte[]) instance.decode((Object) asciiBinary.getBytes(CHARSET_UTF8));
        assertEquals(expected, new String(fromBytes));

        // Path 3: decode(Object) dispatching on a char[] argument.
        final byte[] fromChars = asciiBinary == null
                ? (byte[]) instance.decode((char[]) null)
                : (byte[]) instance.decode(asciiBinary.toCharArray());
        assertEquals(expected, new String(fromChars));
    }

    /**
     * Tests {@code Object decode(Object)} (and the byte[]/char[] overloads) across single-byte,
     * two-byte and null inputs.
     */
    @Test
    void testDecodeObject() throws Exception {
        // Single byte: walk one extra '1' bit in from the right each time.
        assertDecodesTo(new byte[] {(byte) 0b00000000}, "00000000");
        assertDecodesTo(new byte[] {(byte) 0b00000001}, "00000001");
        assertDecodesTo(new byte[] {(byte) 0b00000011}, "00000011");
        assertDecodesTo(new byte[] {(byte) 0b00000111}, "00000111");
        assertDecodesTo(new byte[] {(byte) 0b00001111}, "00001111");
        assertDecodesTo(new byte[] {(byte) 0b00011111}, "00011111");
        assertDecodesTo(new byte[] {(byte) 0b00111111}, "00111111");
        assertDecodesTo(new byte[] {(byte) 0b01111111}, "01111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111}, "11111111");

        // Two bytes: the right-most 8 characters are byte 0, the left-most 8 are byte 1.
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00000000}, "0000000011111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00000001}, "0000000111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00000011}, "0000001111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00000111}, "0000011111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00001111}, "0000111111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00011111}, "0001111111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b00111111}, "0011111111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b01111111}, "0111111111111111");
        assertDecodesTo(new byte[] {(byte) 0b11111111, (byte) 0b11111111}, "1111111111111111");

        // Null input decodes to an empty byte array.
        assertDecodesTo(new byte[0], null);
    }
}
