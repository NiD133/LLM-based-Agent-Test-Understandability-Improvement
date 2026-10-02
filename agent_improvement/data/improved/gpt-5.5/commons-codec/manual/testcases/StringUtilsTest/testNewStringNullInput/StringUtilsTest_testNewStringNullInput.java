package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringNullInput {

    private static final byte[] NULL_BYTES = null;
    private static final String UNKNOWN_CHARSET = "UNKNOWN";

    @Test
    void testNewStringNullInput() {
        assertNull(StringUtils.newString(NULL_BYTES, UNKNOWN_CHARSET));
    }
}
