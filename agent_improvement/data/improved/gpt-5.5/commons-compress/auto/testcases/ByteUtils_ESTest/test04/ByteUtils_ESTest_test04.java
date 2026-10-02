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
public class ByteUtils_ESTest_test04 extends ByteUtils_ESTest_scaffolding {

    private static final int ARRAY_LENGTH = 5;
    private static final long VALUE_TO_WRITE = 4466L;
    private static final int NEGATIVE_OFFSET = (int) (byte) (-1);
    private static final int NEGATIVE_LENGTH = (int) (byte) (-1);

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        byte[] target = new byte[ARRAY_LENGTH];

        ByteUtils.toLittleEndian(target, VALUE_TO_WRITE, NEGATIVE_OFFSET, NEGATIVE_LENGTH);

        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 }, target);
    }
}
