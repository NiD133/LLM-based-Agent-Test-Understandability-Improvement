package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedNullInput {

    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void testGetBytesUncheckedNullInput() {
        assertNull(StringUtils.getBytesUnchecked(null, UNKNOWN_CHARSET_NAME));
    }
}
