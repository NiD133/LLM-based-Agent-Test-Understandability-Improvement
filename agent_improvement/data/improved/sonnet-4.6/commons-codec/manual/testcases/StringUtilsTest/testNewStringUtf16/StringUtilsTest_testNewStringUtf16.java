package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16 {

    // Three ASCII bytes used as raw input for UTF-16 decoding tests
    private static final byte[] SAMPLE_BYTES = { 'a', 'b', 'c' };

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16.name();

        // Compute the JDK reference string once; both CUT methods must match it
        final String expected = new String(SAMPLE_BYTES, charsetName);

        // StringUtils.newString with an explicit charset name must match the JDK result
        assertEquals(expected, StringUtils.newString(SAMPLE_BYTES, charsetName));

        // StringUtils.newStringUtf16 (charset-typed shortcut) must also match
        assertEquals(expected, StringUtils.newStringUtf16(SAMPLE_BYTES));
    }
}
