package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf8 {

    private static final byte[] UTF8_BYTES = { 'a', 'b', 'c' };

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String utf8CharsetName = StandardCharsets.UTF_8.name();

        final String expectedFromNamedCharset = new String(UTF8_BYTES, utf8CharsetName);
        final String actualFromNamedCharset = StringUtils.newString(UTF8_BYTES, utf8CharsetName);
        assertEquals(expectedFromNamedCharset, actualFromNamedCharset);

        final String expectedFromUtf8Helper = new String(UTF8_BYTES, utf8CharsetName);
        final String actualFromUtf8Helper = StringUtils.newStringUtf8(UTF8_BYTES);
        assertEquals(expectedFromUtf8Helper, actualFromUtf8Helper);
    }
}
