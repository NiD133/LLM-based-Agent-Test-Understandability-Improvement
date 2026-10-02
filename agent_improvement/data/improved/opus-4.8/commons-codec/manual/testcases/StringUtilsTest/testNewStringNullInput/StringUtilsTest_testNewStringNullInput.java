package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newString(byte[], String)} when the byte array is {@code null}.
 */
public class StringUtilsTest_testNewStringNullInput {

    /**
     * A {@code null} byte array must yield a {@code null} String, and the charset name
     * is never consulted, so even an unknown charset name does not cause a failure.
     */
    @Test
    void testNewStringNullInput() {
        final String result = StringUtils.newString(null, "UNKNOWN");

        assertNull(result, "newString(null, ...) should return null regardless of the charset name");
    }
}
