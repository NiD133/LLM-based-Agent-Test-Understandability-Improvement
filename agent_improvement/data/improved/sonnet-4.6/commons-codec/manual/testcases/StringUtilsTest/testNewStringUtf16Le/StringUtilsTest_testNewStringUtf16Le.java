package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16Le {

    // Arbitrary bytes used to test the generic newString(byte[], charsetName) path
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    // Properly-structured UTF-16LE bytes: each char is (low-byte, 0x00)
    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();

        // Verify the generic newString(byte[], charsetName) delegates correctly for UTF-16LE
        final String expectedFromGeneric = new String(BYTES_FIXTURE, charsetName);
        final String actualFromGeneric = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expectedFromGeneric, actualFromGeneric);

        // Verify the dedicated newStringUtf16Le(byte[]) convenience method produces the same result
        // as constructing a String directly from well-formed UTF-16LE bytes
        final String expectedFromUtf16LeBytes = new String(BYTES_FIXTURE_16LE, charsetName);
        final String actualFromUtf16LeMethod = StringUtils.newStringUtf16Le(BYTES_FIXTURE_16LE);
        assertEquals(expectedFromUtf16LeBytes, actualFromUtf16LeMethod);
    }
}
