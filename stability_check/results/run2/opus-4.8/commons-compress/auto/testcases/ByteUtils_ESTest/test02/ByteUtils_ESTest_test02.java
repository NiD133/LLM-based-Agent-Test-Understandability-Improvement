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

    /** Value written is 50, which prints as the ASCII character '2'. */
    private static final long VALUE_TO_WRITE = (byte) 50;

    /** Number of little-endian bytes requested (far more than the value needs). */
    private static final int LITTLE_ENDIAN_LENGTH = 212;

    /**
     * Writing the value 50 to a {@link DataOutput} as a little-endian sequence
     * of 212 bytes should emit exactly 212 bytes: the least-significant byte
     * (50, the ASCII character '2') first, followed by 211 zero bytes.
     */
    @Test(timeout = 4000)
    public void writesValueAsLittleEndianAcrossRequestedLength() throws Throwable {
        ByteArrayOutputStream capturedBytes = new ByteArrayOutputStream();
        DataOutput output = new DataOutputStream(capturedBytes);

        ByteUtils.toLittleEndian(output, VALUE_TO_WRITE, LITTLE_ENDIAN_LENGTH);

        // The requested length is exactly the number of bytes written.
        assertEquals(LITTLE_ENDIAN_LENGTH, capturedBytes.size());

        // Little-endian layout: low byte (50 == '2') first, then 211 zero bytes.
        StringBuilder expected = new StringBuilder();
        expected.append('2');
        for (int i = 1; i < LITTLE_ENDIAN_LENGTH; i++) {
            expected.append((char) 0);
        }
        assertEquals(expected.toString(), capturedBytes.toString());
    }
}
