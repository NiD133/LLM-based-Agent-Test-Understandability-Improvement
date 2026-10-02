package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringIso8859_1 {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        // Verify that the generic newString() method correctly decodes ISO-8859-1 bytes
        final String expectedFromGenericMethod = new String(BYTES_FIXTURE, charsetName);
        final String actualFromGenericMethod = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expectedFromGenericMethod, actualFromGenericMethod);

        // Verify that the dedicated newStringIso8859_1() convenience method produces the same result
        final String expectedFromConvenienceMethod = new String(BYTES_FIXTURE, charsetName);
        final String actualFromConvenienceMethod = StringUtils.newStringIso8859_1(BYTES_FIXTURE);
        assertEquals(expectedFromConvenienceMethod, actualFromConvenienceMethod);
    }
}
