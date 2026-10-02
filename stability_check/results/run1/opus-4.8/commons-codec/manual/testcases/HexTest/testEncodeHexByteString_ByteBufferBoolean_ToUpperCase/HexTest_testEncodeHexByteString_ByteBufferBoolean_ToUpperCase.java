package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(ByteBuffer, boolean)} produces
 * upper-case hexadecimal when {@code toLowerCase} is {@code false}.
 */
public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        // A single byte whose upper-case hex representation is "0A".
        final ByteBuffer singleByte = ByteBuffer.allocate(1);
        singleByte.put((byte) 10);
        singleByte.flip();

        // toLowerCase = false -> expect upper-case hex digits.
        final String upperCaseHex = Hex.encodeHexString(singleByte, false);

        assertEquals("0A", upperCaseHex);
    }
}
