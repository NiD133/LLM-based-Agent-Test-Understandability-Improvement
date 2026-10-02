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
public class HexDump_ESTest_test3 extends HexDump_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        byte[] data = new byte[9];
        long displayOffset = 1L;
        PipedWriter appendable = new PipedWriter();
        int startIndex = 0;
        int invalidLength = -186;

        try {
            HexDump.dump(data, displayOffset, (Appendable) appendable, startIndex, invalidLength);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Range [0, 0 + -186) out of bounds for length 9
            //
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
