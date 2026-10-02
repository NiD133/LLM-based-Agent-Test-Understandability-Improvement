package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#newString(byte[], String)} converts the JDK's
 * {@code UnsupportedEncodingException} into an {@code IllegalStateException} when
 * the caller passes a charset name that does not exist.
 */
public class StringUtilsTest_testNewStringBadEnc {

    /** Arbitrary, non-null bytes to decode; the actual content is irrelevant to this test. */
    private static final byte[] BYTES_TO_DECODE = { 'a', 'b', 'c' };

    /** A charset name that is guaranteed not to be registered with the JVM. */
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void newStringWithUnknownCharsetThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> StringUtils.newString(BYTES_TO_DECODE, UNKNOWN_CHARSET_NAME));
    }
}
