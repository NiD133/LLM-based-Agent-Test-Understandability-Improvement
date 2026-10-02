package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#getBytesUtf16Le(String)}.
 *
 * <p>UTF-16LE encoding of the ASCII string "ABC" should match what the JDK
 * produces for the same charset, both through the convenience method
 * {@code getBytesUtf16Le} and through the generic {@code getBytesUnchecked}.</p>
 */
public class StringUtilsTest_testGetBytesUtf16Le {

    /** The string that is encoded in every assertion below. */
    private static final String STRING_FIXTURE = "ABC";

    /** Canonical Java name for the UTF-16LE charset, e.g. "UTF-16LE". */
    private static final String UTF_16LE = StandardCharsets.UTF_16LE.name();

    @Test
    void testGetBytesUtf16Le() throws UnsupportedEncodingException {
        // The JDK's own encoding of "ABC" in UTF-16LE is the reference result.
        final byte[] expected = STRING_FIXTURE.getBytes(UTF_16LE);

        // 1) The generic, charset-name-based method must reproduce it.
        assertArrayEquals(expected, StringUtils.getBytesUnchecked(STRING_FIXTURE, UTF_16LE));

        // 2) The dedicated UTF-16LE convenience method must reproduce it too.
        assertArrayEquals(expected, StringUtils.getBytesUtf16Le(STRING_FIXTURE));
    }
}
