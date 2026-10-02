package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsCS1 {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    // This is valid input for UTF-16BE
    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    // This is valid for UTF-16LE
    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    private static final String STRING_FIXTURE = "ABC";

    private void testGetBytesUnchecked(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    private void testNewString(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsCS1() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }
}
