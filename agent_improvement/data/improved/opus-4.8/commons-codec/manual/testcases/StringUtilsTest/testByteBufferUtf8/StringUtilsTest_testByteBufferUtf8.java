package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#getByteBufferUtf8(String)}.
 */
public class StringUtilsTest_testByteBufferUtf8 {

    @Test
    void testByteBufferUtf8() {
        // A null input should return null rather than throwing.
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");

        // A non-null input should be encoded as its UTF-8 bytes.
        final String text = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";
        final ByteBuffer actualBuffer = StringUtils.getByteBufferUtf8(text);

        final byte[] expectedBytes = text.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expectedBytes, actualBuffer.array());
    }
}
