package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForPrematureEnd {

    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        // Stream contains only 2 bytes but we request reading 3 — should signal premature end
        final ByteArrayInputStream streamWithTwoBytes = new ByteArrayInputStream(new byte[] { 2, 3 });
        assertThrows(IOException.class, () -> fromLittleEndian(streamWithTwoBytes, 3));
    }
}
