package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedBadName {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    @DisplayName("getBytesUnchecked throws IllegalStateException for an unrecognized charset name")
    void testGetBytesUncheckedBadName() {
        // "UNKNOWN" is not a valid charset name; getBytesUnchecked wraps the resulting
        // UnsupportedEncodingException in an IllegalStateException
        assertThrows(IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }
}
