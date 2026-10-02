package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} reports an
 * unusable charset name by wrapping the underlying {@code UnsupportedEncodingException}
 * in an {@link IllegalStateException}.
 */
public class StringUtilsTest_testGetBytesUncheckedBadName {

    /** Text to encode; its content is irrelevant since encoding must fail on the charset name. */
    private static final String STRING_TO_ENCODE = "ABC";

    /** A charset name that does not exist in any JRE. */
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void getBytesUncheckedThrowsIllegalStateExceptionForUnknownCharset() {
        assertThrows(
                IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_TO_ENCODE, UNKNOWN_CHARSET_NAME));
    }
}
