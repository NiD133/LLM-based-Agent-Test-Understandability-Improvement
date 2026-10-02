package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedBadName {

    private static final String STRING_FIXTURE = "ABC";
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    void testGetBytesUncheckedBadName() {
        assertThrows(
                IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, UNKNOWN_CHARSET_NAME));
    }
}
