package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(byte[])}, which converts raw bytes into a
 * String of '0' and '1' characters.
 */
public class BinaryCodecTest_testEncodeByteArray {

    /** The binary codec under test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /**
     * Encodes the given raw bytes and asserts that the resulting binary String
     * matches the expectation.
     *
     * <p>
     * The raw bytes are supplied in array order ({@code raw[0]}, {@code raw[1]},
     * ...). In the encoded String the highest-indexed byte appears first, so a
     * two-byte array {@code {low, high}} encodes to {@code <high><low>}.
     * </p>
     *
     * @param expectedBinary the expected String of '0' and '1' characters.
     * @param rawBytes       the raw byte values to encode.
     */
    private void assertEncodesTo(final String expectedBinary, final int... rawBytes) {
        final byte[] raw = new byte[rawBytes.length];
        for (int i = 0; i < rawBytes.length; i++) {
            raw[i] = (byte) rawBytes[i];
        }
        assertEquals(expectedBinary, new String(instance.encode(raw)));
    }

    @Test
    void testEncodeByteArray() {
        // A single raw byte, setting progressively more low-order bits.
        assertEncodesTo("00000000", 0b00000000);
        assertEncodesTo("00000001", 0b00000001);
        assertEncodesTo("00000011", 0b00000011);
        assertEncodesTo("00000111", 0b00000111);
        assertEncodesTo("00001111", 0b00001111);
        assertEncodesTo("00011111", 0b00011111);
        assertEncodesTo("00111111", 0b00111111);
        assertEncodesTo("01111111", 0b01111111);
        assertEncodesTo("11111111", 0b11111111);

        // Two raw bytes, filling the low-order byte first (high-order byte = 0).
        assertEncodesTo("0000000000000000", 0b00000000, 0b00000000);
        assertEncodesTo("0000000000000001", 0b00000001, 0b00000000);
        assertEncodesTo("0000000000000011", 0b00000011, 0b00000000);
        assertEncodesTo("0000000000000111", 0b00000111, 0b00000000);
        assertEncodesTo("0000000000001111", 0b00001111, 0b00000000);
        assertEncodesTo("0000000000011111", 0b00011111, 0b00000000);
        assertEncodesTo("0000000000111111", 0b00111111, 0b00000000);
        assertEncodesTo("0000000001111111", 0b01111111, 0b00000000);
        assertEncodesTo("0000000011111111", 0b11111111, 0b00000000);

        // Two raw bytes, now also setting bits in the high-order byte
        // (low-order byte stays fully set at 11111111).
        assertEncodesTo("0000000111111111", 0b11111111, 0b00000001);
        assertEncodesTo("0000001111111111", 0b11111111, 0b00000011);
        assertEncodesTo("0000011111111111", 0b11111111, 0b00000111);
        assertEncodesTo("0000111111111111", 0b11111111, 0b00001111);
        assertEncodesTo("0001111111111111", 0b11111111, 0b00011111);
        assertEncodesTo("0011111111111111", 0b11111111, 0b00111111);
        assertEncodesTo("0111111111111111", 0b11111111, 0b01111111);
        assertEncodesTo("1111111111111111", 0b11111111, 0b11111111);

        // A null array encodes to an empty result.
        assertEquals(0, instance.encode((byte[]) null).length);
    }
}
