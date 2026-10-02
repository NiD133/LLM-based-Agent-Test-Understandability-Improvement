package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#toByteArray(String)}, which parses a string of '0'/'1'
 * characters into the raw bytes it represents.
 *
 * <p>
 * Note on bit ordering: within the 8-character group for a byte, the right-most
 * character is the least-significant bit. So "00000001" decodes to the byte value
 * {@code 0x01}, and "10000000" would decode to {@code 0x80}. When several bytes are
 * encoded, the right-most group maps to byte index 0 of the result.
 * </p>
 */
public class BinaryCodecTest_testToByteArrayFromString {

    /** The codec under test, recreated fresh before each test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        this.instance = null;
    }

    /**
     * Decodes {@code binaryString} with {@link BinaryCodec#toByteArray(String)} and
     * asserts the result equals {@code expectedBytes}.
     *
     * <p>
     * The comparison is done on the {@link String} view of both byte arrays, matching
     * the original test's expectation semantics.
     * </p>
     */
    private void assertToByteArray(final String binaryString, final byte[] expectedBytes) {
        final byte[] actual = instance.toByteArray(binaryString);
        assertEquals(new String(expectedBytes), new String(actual));
    }

    @Test
    void testToByteArrayFromString() {
        // Single byte: progressively raise low bits from none to all eight.
        assertToByteArray("00000000", new byte[] {(byte) 0x00});
        assertToByteArray("00000001", new byte[] {(byte) 0x01});
        assertToByteArray("00000011", new byte[] {(byte) 0x03});
        assertToByteArray("00000111", new byte[] {(byte) 0x07});
        assertToByteArray("00001111", new byte[] {(byte) 0x0F});
        assertToByteArray("00011111", new byte[] {(byte) 0x1F});
        assertToByteArray("00111111", new byte[] {(byte) 0x3F});
        assertToByteArray("01111111", new byte[] {(byte) 0x7F});
        assertToByteArray("11111111", new byte[] {(byte) 0xFF});

        // Two bytes: byte index 0 (right-most group) is always full (0xFF);
        // byte index 1 (left-most group) ramps up bit by bit.
        assertToByteArray("0000000011111111", new byte[] {(byte) 0xFF, (byte) 0x00});
        assertToByteArray("0000000111111111", new byte[] {(byte) 0xFF, (byte) 0x01});
        assertToByteArray("0000001111111111", new byte[] {(byte) 0xFF, (byte) 0x03});
        assertToByteArray("0000011111111111", new byte[] {(byte) 0xFF, (byte) 0x07});
        assertToByteArray("0000111111111111", new byte[] {(byte) 0xFF, (byte) 0x0F});
        assertToByteArray("0001111111111111", new byte[] {(byte) 0xFF, (byte) 0x1F});
        assertToByteArray("0011111111111111", new byte[] {(byte) 0xFF, (byte) 0x3F});
        assertToByteArray("0111111111111111", new byte[] {(byte) 0xFF, (byte) 0x7F});
        assertToByteArray("1111111111111111", new byte[] {(byte) 0xFF, (byte) 0xFF});

        // A null string decodes to an empty byte array.
        assertEquals(0, instance.toByteArray((String) null).length);
    }
}
