package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test5 extends HexDump_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        final byte[] input = new byte[9];
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            HexDump.dump(input, (long) (byte) 0, (OutputStream) output, (int) (byte) (-1));
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException exception) {
            //
            // illegal index: -1 into array of length 9
            //
            verifyException("org.apache.commons.io.HexDump", exception);
        }
    }
}
