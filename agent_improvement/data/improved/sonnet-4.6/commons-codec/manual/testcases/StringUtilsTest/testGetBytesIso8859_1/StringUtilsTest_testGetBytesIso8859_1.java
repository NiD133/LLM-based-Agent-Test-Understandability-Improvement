package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesIso8859_1 {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        // Reference bytes produced by the standard JDK API for ISO-8859-1
        final byte[] expectedBytes = STRING_FIXTURE.getBytes(charsetName);

        // getBytesUnchecked with the charset name should match the JDK reference
        assertArrayEquals(expectedBytes, StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName));

        // The ISO-8859-1 convenience method should produce the same result
        assertArrayEquals(expectedBytes, StringUtils.getBytesIso8859_1(STRING_FIXTURE));
    }
}
