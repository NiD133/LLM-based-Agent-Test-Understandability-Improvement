package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForPrematureEnd {

    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        final byte[] availableBytes = { 2, 3 };
        final int requestedByteCount = 3;
        final DataInput input = new DataInputStream(new ByteArrayInputStream(availableBytes));

        assertThrows(EOFException.class, () -> fromLittleEndian(input, requestedByteCount));
    }
}
