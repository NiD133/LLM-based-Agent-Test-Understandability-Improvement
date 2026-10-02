package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test02 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(DataOutput, long, int)} writes
     * exactly {@code length} bytes to the underlying stream, with the value encoded
     * little-endian (lowest byte first) and the remaining higher bytes padded with zeros.
     */
    @Test(timeout = 4000)
    public void writesValueAsLittleEndianPaddedToRequestedLength() throws Throwable {
        ByteArrayOutputStream capturedBytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(capturedBytes);

        long valueToWrite = (byte) 50;
        int lengthInBytes = 212;
        ByteUtils.toLittleEndian((DataOutput) output, valueToWrite, lengthInBytes);

        // The method must write exactly the requested number of bytes.
        assertEquals(lengthInBytes, capturedBytes.size());

        // Little-endian layout: the lowest byte (50) comes first, followed by
        // (lengthInBytes - 1) zero bytes for the higher-order positions.
        StringBuilder expected = new StringBuilder();
        expected.append((char) 50);
        for (int i = 1; i < lengthInBytes; i++) {
            expected.append((char) 0);
        }
        assertEquals(expected.toString(), capturedBytes.toString());
    }
}
