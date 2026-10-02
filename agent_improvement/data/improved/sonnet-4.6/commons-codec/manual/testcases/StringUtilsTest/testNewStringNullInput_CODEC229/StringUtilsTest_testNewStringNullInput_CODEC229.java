package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Regression test for CODEC-229: all newStringXxx methods must return null
 * (not throw NullPointerException) when the input byte array is null.
 */
public class StringUtilsTest_testNewStringNullInput_CODEC229 {

    @Test
    void testNewStringNullInput_CODEC229() {
        assertNull(StringUtils.newStringUtf8(null));
        assertNull(StringUtils.newStringIso8859_1(null));
        assertNull(StringUtils.newStringUsAscii(null));
        assertNull(StringUtils.newStringUtf16(null));
        assertNull(StringUtils.newStringUtf16Be(null));
        assertNull(StringUtils.newStringUtf16Le(null));
    }
}
