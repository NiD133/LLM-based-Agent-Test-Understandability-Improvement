package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testByteBufferUtf8 {

    private static final String UTF_8_TEXT = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";

    @Test
    void testByteBufferUtf8() {
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");

        final ByteBuffer byteBuffer = StringUtils.getByteBufferUtf8(UTF_8_TEXT);

        assertArrayEquals(UTF_8_TEXT.getBytes(StandardCharsets.UTF_8), byteBuffer.array());
    }
}
