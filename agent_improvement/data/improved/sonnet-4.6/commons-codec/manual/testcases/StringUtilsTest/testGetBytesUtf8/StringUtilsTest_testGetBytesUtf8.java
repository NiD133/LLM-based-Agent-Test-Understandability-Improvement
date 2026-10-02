package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf8 {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();

        // Verify getBytesUnchecked produces the same bytes as String.getBytes for UTF-8
        final byte[] expectedFromUnchecked = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUnchecked = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expectedFromUnchecked, actualFromUnchecked);

        // Verify the dedicated getBytesUtf8 convenience method matches String.getBytes("UTF-8")
        final byte[] expectedFromUtf8 = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUtf8 = StringUtils.getBytesUtf8(STRING_FIXTURE);
        assertArrayEquals(expectedFromUtf8, actualFromUtf8);
    }
}
