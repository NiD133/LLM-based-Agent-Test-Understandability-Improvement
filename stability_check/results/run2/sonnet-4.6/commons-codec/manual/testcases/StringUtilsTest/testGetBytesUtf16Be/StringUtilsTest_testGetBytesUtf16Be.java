package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16Be {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        // Verify getBytesUnchecked produces the same encoding as the standard Java API
        final byte[] expectedFromUnchecked = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUnchecked = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expectedFromUnchecked, actualFromUnchecked);

        // Verify the dedicated getBytesUtf16Be method produces the same result
        final byte[] expectedFromDirect = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromDirect = StringUtils.getBytesUtf16Be(STRING_FIXTURE);
        assertArrayEquals(expectedFromDirect, actualFromDirect);
    }
}
