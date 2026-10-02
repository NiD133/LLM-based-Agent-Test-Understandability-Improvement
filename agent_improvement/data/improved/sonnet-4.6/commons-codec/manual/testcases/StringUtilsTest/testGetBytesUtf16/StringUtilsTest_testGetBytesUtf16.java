package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16 {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();

        // Verify that the unchecked variant produces the same bytes as the standard JDK method
        final byte[] expectedFromUnchecked = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUnchecked = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expectedFromUnchecked, actualFromUnchecked);

        // Verify that the UTF-16 convenience method produces the same bytes as the standard JDK method
        final byte[] expectedFromJdk = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUtf16Method = StringUtils.getBytesUtf16(STRING_FIXTURE);
        assertArrayEquals(expectedFromJdk, actualFromUtf16Method);
    }
}
