package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedBadName {

    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUncheckedBadName() {
        // An unrecognised charset name must be wrapped in IllegalStateException, not propagated as checked.
        assertThrows(IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }
}
