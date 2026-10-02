package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringBadEnc {

    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };
    private static final String UNKNOWN_CHARSET = "UNKNOWN";

    @Test
    void testNewStringBadEnc() {
        assertThrows(IllegalStateException.class, () -> StringUtils.newString(ASCII_BYTES, UNKNOWN_CHARSET));
    }
}
