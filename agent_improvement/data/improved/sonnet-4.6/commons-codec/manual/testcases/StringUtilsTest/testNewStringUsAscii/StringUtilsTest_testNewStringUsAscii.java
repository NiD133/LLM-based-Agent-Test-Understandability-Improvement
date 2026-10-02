package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUsAscii {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    @Test
    void testNewStringUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();

        // Verify the generic newString(byte[], String) handles US-ASCII correctly
        final String expectedGeneric = new String(BYTES_FIXTURE, charsetName);
        final String actualGeneric = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expectedGeneric, actualGeneric);

        // Verify the US-ASCII convenience method produces the same result
        final String expectedUsAscii = new String(BYTES_FIXTURE, charsetName);
        final String actualUsAscii = StringUtils.newStringUsAscii(BYTES_FIXTURE);
        assertEquals(expectedUsAscii, actualUsAscii);
    }
}
