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

        // Verify getBytesUnchecked delegates correctly to String.getBytes for UTF-16BE
        assertArrayEquals(
            STRING_FIXTURE.getBytes(charsetName),
            StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName)
        );

        // Verify the dedicated getBytesUtf16Be convenience method matches String.getBytes(UTF-16BE)
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf16Be(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }
}
