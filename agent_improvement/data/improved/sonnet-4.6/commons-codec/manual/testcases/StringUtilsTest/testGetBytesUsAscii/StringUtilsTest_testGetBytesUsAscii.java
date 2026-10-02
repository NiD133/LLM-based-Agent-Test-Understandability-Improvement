package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUsAscii {

    private static final String STRING_FIXTURE = "ABC";

    /**
     * Asserts that getBytesUnchecked produces the same bytes as the JDK's String.getBytes for the given charset,
     * confirming the unchecked wrapper delegates correctly without altering the encoding output.
     */
    private void assertBytesUncheckedMatchJdk(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();

        // Confirm the generic unchecked variant correctly encodes using US-ASCII
        assertBytesUncheckedMatchJdk(charsetName);

        // Confirm the US-ASCII convenience method produces bytes identical to String.getBytes("US-ASCII")
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUsAscii(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }
}
