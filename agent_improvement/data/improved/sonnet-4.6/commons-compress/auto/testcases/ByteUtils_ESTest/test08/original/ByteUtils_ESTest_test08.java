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
public class ByteUtils_ESTest_test08 extends ByteUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        ByteUtils.ByteSupplier byteUtils_ByteSupplier0 = mock(ByteUtils.ByteSupplier.class, new ViolatedAssumptionAnswer());
        doReturn((-1)).when(byteUtils_ByteSupplier0).getAsByte();
        try {
            ByteUtils.fromLittleEndian(byteUtils_ByteSupplier0, 1);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Premature end of data
            //
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
