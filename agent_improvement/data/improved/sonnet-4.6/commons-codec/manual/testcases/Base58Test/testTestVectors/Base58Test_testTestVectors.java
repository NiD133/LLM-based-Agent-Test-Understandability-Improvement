package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class Base58Test_testTestVectors {

    @Test
    void testTestVectors() {
        final String helloWorld = "Hello World!";
        final String foxSentence = "The quick brown fox jumps over the lazy dog.";
        // 0x0000287fb4cd as a 6-byte array (48-bit value with two leading zero bytes)
        final byte[] hexValueBytes = extractSixBytesFromLong(0x0000287fb4cdL);

        final Base58 codec = new Base58();

        // Encode and verify expected Base58 output
        final byte[] encodedHelloWorld = codec.encode(StringUtils.getBytesUtf8(helloWorld));
        final byte[] encodedFoxSentence = codec.encode(StringUtils.getBytesUtf8(foxSentence));
        final byte[] encodedHexValue = codec.encode(hexValueBytes);

        assertEquals("2NEpo7TZRRrLZSi2U", StringUtils.newStringUtf8(encodedHelloWorld), "encoding 'Hello World!'");
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", StringUtils.newStringUtf8(encodedFoxSentence));
        assertEquals("11233QC4", StringUtils.newStringUtf8(encodedHexValue), "encoding 0x0000287fb4cd");

        // Decode and verify round-trip
        assertEquals(helloWorld, StringUtils.newStringUtf8(codec.decode(encodedHelloWorld)), "decoding 'Hello World!'");
        assertEquals(foxSentence, StringUtils.newStringUtf8(codec.decode(encodedFoxSentence)));
        assertArrayEquals(hexValueBytes, codec.decode(encodedHexValue), "decoding 0x0000287fb4cd");
    }

    private static byte[] extractSixBytesFromLong(final long value) {
        // A long occupies 8 bytes; skip the first 2 to obtain the low 6 bytes
        final byte[] eightBytes = ByteBuffer.allocate(8).putLong(value).array();
        final byte[] sixBytes = new byte[6];
        System.arraycopy(eightBytes, 2, sixBytes, 0, 6);
        return sixBytes;
    }
}
