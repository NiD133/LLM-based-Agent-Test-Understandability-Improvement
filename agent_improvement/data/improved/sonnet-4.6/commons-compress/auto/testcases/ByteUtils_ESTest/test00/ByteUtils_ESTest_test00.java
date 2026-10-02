package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test00 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that toLittleEndian(byte[], long, int, int) throws ArrayIndexOutOfBoundsException
     * when the requested write length (6) exceeds the capacity of the destination array (2 bytes).
     * Writing starts at offset 0, so access to index 2 triggers the exception.
     */
    @Test(timeout = 4000)
    public void test00_toLittleEndian_throwsWhenLengthExceedsArrayCapacity() throws Throwable {
        byte[] twoByteBuffer = new byte[2];
        long value = -1L;
        int startOffset = 0;
        int bytesToWrite = 6; // exceeds twoByteBuffer.length, triggering ArrayIndexOutOfBoundsException at index 2

        try {
            ByteUtils.toLittleEndian(twoByteBuffer, value, startOffset, bytesToWrite);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // 2
            //
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
