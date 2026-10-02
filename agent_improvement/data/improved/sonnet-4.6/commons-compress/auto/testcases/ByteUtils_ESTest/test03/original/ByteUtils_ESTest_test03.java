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
public class ByteUtils_ESTest_test03 extends ByteUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        File file0 = MockFile.createTempFile("suffixes", "");
        MockPrintStream mockPrintStream0 = new MockPrintStream(file0);
        ByteUtils.OutputStreamByteConsumer byteUtils_OutputStreamByteConsumer0 = new ByteUtils.OutputStreamByteConsumer(mockPrintStream0);
        ByteUtils.toLittleEndian((ByteUtils.ByteConsumer) byteUtils_OutputStreamByteConsumer0, (long) (byte) 77, 1);
        assertEquals(1L, file0.length());
    }
}
