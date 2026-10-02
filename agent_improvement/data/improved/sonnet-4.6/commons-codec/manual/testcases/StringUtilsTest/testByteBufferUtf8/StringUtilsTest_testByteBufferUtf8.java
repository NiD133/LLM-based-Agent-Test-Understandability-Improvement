package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testByteBufferUtf8 {

    // A long mixed-character string used to exercise UTF-8 byte encoding
    private static final String SAMPLE_TEXT = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";

    @Test
    void testByteBufferUtf8() {
        // null input must produce a null ByteBuffer (null-safety contract)
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");

        // Non-null input must produce a ByteBuffer whose backing array equals
        // the standard UTF-8 encoding of the same string
        ByteBuffer result = StringUtils.getByteBufferUtf8(SAMPLE_TEXT);
        byte[] expectedBytes = SAMPLE_TEXT.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(expectedBytes, result.array());
    }
}
