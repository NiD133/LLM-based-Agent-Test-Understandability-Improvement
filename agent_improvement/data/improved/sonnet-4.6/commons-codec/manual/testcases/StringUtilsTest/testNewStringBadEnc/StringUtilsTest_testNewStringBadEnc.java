package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringBadEnc {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    @Test
    @DisplayName("newString with unknown charset name throws IllegalStateException")
    void testNewStringBadEnc() {
        assertThrows(IllegalStateException.class,
                () -> StringUtils.newString(BYTES_FIXTURE, "UNKNOWN"));
    }
}
