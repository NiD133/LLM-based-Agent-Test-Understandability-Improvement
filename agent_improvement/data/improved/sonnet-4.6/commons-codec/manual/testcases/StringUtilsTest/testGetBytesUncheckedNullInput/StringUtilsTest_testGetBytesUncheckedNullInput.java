package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedNullInput {

    @Test
    void testGetBytesUncheckedNullInput() {
        // getBytesUnchecked must return null when the input string is null,
        // regardless of the charset name supplied.
        assertNull(StringUtils.getBytesUnchecked(null, "UNKNOWN"));
    }
}
