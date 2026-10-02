package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16Le {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();

        // Verify getBytesUnchecked(string, "UTF-16LE") matches standard String.getBytes("UTF-16LE")
        final byte[] expectedFromUnchecked = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUnchecked = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expectedFromUnchecked, actualFromUnchecked);

        // Verify the convenience method getBytesUtf16Le also matches standard String.getBytes("UTF-16LE")
        final byte[] expectedFromGetBytes = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actualFromUtf16Le = StringUtils.getBytesUtf16Le(STRING_FIXTURE);
        assertArrayEquals(expectedFromGetBytes, actualFromUtf16Le);
    }
}
