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
public class ByteUtils_ESTest_test02 extends ByteUtils_ESTest_scaffolding {

    // ASCII '2' = byte 50; toLittleEndian with 212 bytes yields '2' followed by 211 zero bytes
    private static final long VALUE = (long) (byte) 50;
    private static final int BYTE_COUNT = 212;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputBuffer);

        ByteUtils.toLittleEndian((DataOutput) dataOutput, VALUE, BYTE_COUNT);

        // first byte is '2' (ASCII 50), remaining 211 bytes are zero
        String expectedOutput = "2" + new String(new char[211]);
        assertEquals(BYTE_COUNT, outputBuffer.size());
        assertEquals(expectedOutput, outputBuffer.toString());
    }
}
