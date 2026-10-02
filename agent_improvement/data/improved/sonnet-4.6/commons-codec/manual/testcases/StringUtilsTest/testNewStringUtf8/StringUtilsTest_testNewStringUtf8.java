package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf8 {

    // ASCII bytes used as input for decoding tests
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();

        // StringUtils.newString must decode bytes the same way as the JDK String constructor
        final String expectedFromNewString = new String(BYTES_FIXTURE, charsetName);
        final String actualFromNewString = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expectedFromNewString, actualFromNewString);

        // StringUtils.newStringUtf8 must produce the same result as new String(bytes, "UTF-8")
        final String expectedUtf8 = new String(BYTES_FIXTURE, charsetName);
        final String actualUtf8 = StringUtils.newStringUtf8(BYTES_FIXTURE);
        assertEquals(expectedUtf8, actualUtf8);
    }
}
