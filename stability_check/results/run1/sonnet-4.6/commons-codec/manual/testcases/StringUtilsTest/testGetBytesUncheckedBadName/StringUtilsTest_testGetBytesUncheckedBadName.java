package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUncheckedBadName {

    private static final String STRING_FIXTURE = "ABC";

    /**
     * Verifies that getBytesUnchecked wraps UnsupportedEncodingException in
     * IllegalStateException when the charset name is not recognized by the JRE.
     */
    @Test
    void testGetBytesUncheckedBadName() {
        assertThrows(IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, "UNKNOWN"));
    }
}
