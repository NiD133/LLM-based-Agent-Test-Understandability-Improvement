package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test07 extends ByteUtils_ESTest_scaffolding {

    private static final int BUFFER_SIZE = 7;
    private static final int STREAM_OFFSET = 2;
    private static final int STREAM_LENGTH = 2;
    private static final int BYTES_TO_READ = 1;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        byte[] zeroFilledBuffer = new byte[BUFFER_SIZE];
        ByteArrayInputStream inputStream = new ByteArrayInputStream(zeroFilledBuffer, (byte) STREAM_OFFSET, (byte) STREAM_LENGTH);
        DataInputStream dataInput = new DataInputStream(inputStream);

        long value = ByteUtils.fromLittleEndian((DataInput) dataInput, (int) (byte) BYTES_TO_READ);

        assertEquals(1, inputStream.available());
        assertEquals(0L, value);
    }
}
