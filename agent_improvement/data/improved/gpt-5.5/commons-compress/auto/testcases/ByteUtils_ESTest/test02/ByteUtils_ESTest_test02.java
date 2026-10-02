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

    private static final int LITTLE_ENDIAN_BYTE_COUNT = 212;
    private static final long VALUE_TO_WRITE = (long) (byte) 50;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        ByteArrayOutputStream writtenBytes = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(writtenBytes);

        ByteUtils.toLittleEndian((DataOutput) dataOutput, VALUE_TO_WRITE, LITTLE_ENDIAN_BYTE_COUNT);

        assertEquals(212, writtenBytes.size());
        assertEquals(expectedLittleEndianString(), writtenBytes.toString());
    }

    private static String expectedLittleEndianString() {
        char[] expectedCharacters = new char[LITTLE_ENDIAN_BYTE_COUNT];
        expectedCharacters[0] = '2';
        return new String(expectedCharacters);
    }
}
