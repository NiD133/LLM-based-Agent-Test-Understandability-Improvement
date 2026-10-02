package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForPrematureEnd {

    /**
     * When {@link ByteUtils#fromLittleEndian(DataInput, int)} is asked to read more bytes
     * than the underlying input actually holds, it should fail with an {@link EOFException}
     * once the stream runs out of data.
     */
    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        // Input supplies only 2 bytes...
        final DataInput input = new DataInputStream(new ByteArrayInputStream(new byte[] { 2, 3 }));

        // ...but we ask to read 3, so reading past the end must throw.
        final int bytesToRead = 3;
        assertThrows(EOFException.class, () -> fromLittleEndian(input, bytesToRead));
    }
}
