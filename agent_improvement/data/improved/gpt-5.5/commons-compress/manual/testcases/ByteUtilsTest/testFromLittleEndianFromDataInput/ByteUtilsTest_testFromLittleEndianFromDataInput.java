package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInput {

    @Test
    void testFromLittleEndianFromDataInput() throws IOException {
        final byte[] littleEndianBytes = { 2, 3, 4, 5 };
        final DataInput din = new DataInputStream(new ByteArrayInputStream(littleEndianBytes));

        final long expectedValueFromFirstThreeBytes = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expectedValueFromFirstThreeBytes, fromLittleEndian(din, 3));
    }
}
