package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16Le {

    private static final String TEXT_TO_ENCODE = "ABC";

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        final String utf16LeCharsetName = StandardCharsets.UTF_16LE.name();

        assertGetBytesUncheckedMatchesJdkEncoding(utf16LeCharsetName);

        final byte[] expectedUtf16LeBytes = TEXT_TO_ENCODE.getBytes(utf16LeCharsetName);
        final byte[] actualUtf16LeBytes = StringUtils.getBytesUtf16Le(TEXT_TO_ENCODE);
        assertArrayEquals(expectedUtf16LeBytes, actualUtf16LeBytes);
    }

    private void assertGetBytesUncheckedMatchesJdkEncoding(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expectedBytes = TEXT_TO_ENCODE.getBytes(charsetName);
        final byte[] actualBytes = StringUtils.getBytesUnchecked(TEXT_TO_ENCODE, charsetName);
        assertArrayEquals(expectedBytes, actualBytes);
    }
}
