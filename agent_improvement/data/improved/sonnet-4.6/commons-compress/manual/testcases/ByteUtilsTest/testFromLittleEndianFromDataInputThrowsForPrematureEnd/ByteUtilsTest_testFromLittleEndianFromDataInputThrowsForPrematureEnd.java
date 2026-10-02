package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForPrematureEnd {

    // The stream holds fewer bytes than the requested read length, triggering EOFException.
    private static final int AVAILABLE_BYTE_COUNT = 2;
    private static final int REQUESTED_BYTE_COUNT = 3;

    @Test
    void testFromLittleEndianFromDataInputThrowsForPrematureEnd() {
        byte[] twoBytes = new byte[] { 2, 3 };
        DataInput dataInput = new DataInputStream(new ByteArrayInputStream(twoBytes));

        assertThrows(EOFException.class,
                () -> fromLittleEndian(dataInput, REQUESTED_BYTE_COUNT),
                "Reading " + REQUESTED_BYTE_COUNT + " bytes from a stream with only "
                        + AVAILABLE_BYTE_COUNT + " bytes should throw EOFException");
    }
}
