package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

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
