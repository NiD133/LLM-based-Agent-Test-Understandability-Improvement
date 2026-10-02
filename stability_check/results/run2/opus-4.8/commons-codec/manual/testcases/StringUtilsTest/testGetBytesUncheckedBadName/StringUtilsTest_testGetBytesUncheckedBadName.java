package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} rejects an
 * unknown charset name by translating the underlying
 * {@code UnsupportedEncodingException} into an {@link IllegalStateException}.
 */
public class StringUtilsTest_testGetBytesUncheckedBadName {

    /** Any non-null string works; its content is irrelevant to this test. */
    private static final String ANY_STRING = "ABC";

    /** A charset name that is guaranteed not to exist in the JRE. */
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void encodingWithUnknownCharsetNameThrowsIllegalState() {
        assertThrows(IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(ANY_STRING, UNKNOWN_CHARSET_NAME));
    }
}
