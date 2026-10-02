package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} returns
 * {@code null} when the input string is {@code null}, regardless of the charset
 * name supplied.
 */
public class StringUtilsTest_testGetBytesUncheckedNullInput {

    @Test
    void getBytesUncheckedReturnsNullForNullString() {
        // A null input string short-circuits before the charset is ever used,
        // so even an unknown charset name (which would otherwise be invalid)
        // does not trigger an exception.
        final byte[] result = StringUtils.getBytesUnchecked(null, "UNKNOWN");

        assertNull(result);
    }
}
