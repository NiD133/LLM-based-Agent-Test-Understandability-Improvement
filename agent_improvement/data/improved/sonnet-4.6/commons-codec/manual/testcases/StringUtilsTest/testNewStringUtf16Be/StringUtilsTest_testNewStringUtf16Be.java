package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16Be {

    // Single-byte ASCII sequence used to verify newString() with a charset name parameter
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    // Valid UTF-16BE encoding of "abc": each character occupies two bytes (big-endian)
    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    @Test
    void testNewStringUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        // Verify that StringUtils.newString(bytes, charsetName) matches the JDK String constructor
        final String expectedFromNewString = new String(BYTES_FIXTURE, charsetName);
        final String actualFromNewString = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expectedFromNewString, actualFromNewString);

        // Verify that the UTF-16BE convenience method matches the JDK String constructor
        final String expectedFromUtf16Be = new String(BYTES_FIXTURE_16BE, charsetName);
        final String actualFromUtf16Be = StringUtils.newStringUtf16Be(BYTES_FIXTURE_16BE);
        assertEquals(expectedFromUtf16Be, actualFromUtf16Be);
    }
}
