package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} reports an
 * unknown charset name by throwing an {@link IllegalStateException} rather than
 * the checked {@code UnsupportedEncodingException} it wraps.
 */
public class StringUtilsTest_testGetBytesUncheckedBadName {

    /** Any non-null string to encode; its content is irrelevant to this test. */
    private static final String INPUT_STRING = "ABC";

    /** A charset name that is guaranteed not to be a valid/known charset. */
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void getBytesUncheckedWithUnknownCharsetThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(INPUT_STRING, UNKNOWN_CHARSET_NAME));
    }
}
