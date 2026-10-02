package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class Base58Test_testTestVectors {

    private static final String HELLO_WORLD_TEXT = "Hello World!";
    private static final String HELLO_WORLD_BASE58 = "2NEpo7TZRRrLZSi2U";

    private static final String PANGRAM_TEXT = "The quick brown fox jumps over the lazy dog.";
    private static final String PANGRAM_BASE58 = "USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z";

    private static final long FORTY_EIGHT_BIT_VALUE = 0x0000287fb4cdL;
    private static final String FORTY_EIGHT_BIT_VALUE_BASE58 = "11233QC4";

    @Test
    void testTestVectors() {
        final byte[] encodedHelloWorld = new Base58().encode(StringUtils.getBytesUtf8(HELLO_WORLD_TEXT));
        final byte[] encodedPangram = new Base58().encode(StringUtils.getBytesUtf8(PANGRAM_TEXT));

        final byte[] valueBytes = ByteBuffer.allocate(8).putLong(FORTY_EIGHT_BIT_VALUE).array();
        final byte[] valueBytesWithoutLongPadding = new byte[6];
        System.arraycopy(valueBytes, 2, valueBytesWithoutLongPadding, 0, 6);
        final byte[] encodedValue = new Base58().encode(valueBytesWithoutLongPadding);

        assertEquals(HELLO_WORLD_BASE58, StringUtils.newStringUtf8(encodedHelloWorld), "encoding hello world");
        assertEquals(PANGRAM_BASE58, StringUtils.newStringUtf8(encodedPangram));
        assertEquals(FORTY_EIGHT_BIT_VALUE_BASE58, StringUtils.newStringUtf8(encodedValue), "encoding 0x0000287fb4cd");

        final byte[] decodedHelloWorld = new Base58().decode(encodedHelloWorld);
        final byte[] decodedPangram = new Base58().decode(encodedPangram);
        final byte[] decodedValue = new Base58().decode(encodedValue);

        assertEquals(HELLO_WORLD_TEXT, StringUtils.newStringUtf8(decodedHelloWorld), "decoding hello world");
        assertEquals(PANGRAM_TEXT, StringUtils.newStringUtf8(decodedPangram));
        assertArrayEquals(valueBytesWithoutLongPadding, decodedValue, "decoding 0x0000287fb4cd");
    }
}
