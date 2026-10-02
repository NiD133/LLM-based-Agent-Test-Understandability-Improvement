package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringNullInput {

    @Test
    @DisplayName("newString returns null when the input byte array is null, regardless of charset name")
    void testNewStringNullInput() {
        // "UNKNOWN" is intentionally invalid — the null-check must occur before charset lookup
        assertNull(StringUtils.newString(null, "UNKNOWN"));
    }
}
