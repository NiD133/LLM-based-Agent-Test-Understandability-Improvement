package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForPrematureEnd {

    @Test
    void testFromLittleEndianFromStreamThrowsForPrematureEnd() {
        final ByteArrayInputStream twoByteStream = new ByteArrayInputStream(new byte[] { 2, 3 });

        assertThrows(IOException.class, () -> fromLittleEndian(twoByteStream, 3));
    }
}
