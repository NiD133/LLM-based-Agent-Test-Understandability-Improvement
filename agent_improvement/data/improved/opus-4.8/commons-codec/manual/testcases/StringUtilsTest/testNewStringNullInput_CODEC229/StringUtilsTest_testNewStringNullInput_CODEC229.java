package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Regression test for CODEC-229.
 *
 * <p>Every charset-specific {@code newString*} factory in {@link StringUtils} must
 * treat a {@code null} byte array as "no data" and return {@code null}, rather than
 * throwing a {@link NullPointerException}.</p>
 */
public class StringUtilsTest_testNewStringNullInput_CODEC229 {

    /** Passing {@code null} bytes to any of the {@code newString*} variants returns {@code null}. */
    @Test
    void newStringFactoriesReturnNullForNullBytes() {
        final byte[] nullBytes = null;

        assertNull(StringUtils.newStringUtf8(nullBytes), "newStringUtf8(null)");
        assertNull(StringUtils.newStringIso8859_1(nullBytes), "newStringIso8859_1(null)");
        assertNull(StringUtils.newStringUsAscii(nullBytes), "newStringUsAscii(null)");
        assertNull(StringUtils.newStringUtf16(nullBytes), "newStringUtf16(null)");
        assertNull(StringUtils.newStringUtf16Be(nullBytes), "newStringUtf16Be(null)");
        assertNull(StringUtils.newStringUtf16Le(nullBytes), "newStringUtf16Le(null)");
    }
}
