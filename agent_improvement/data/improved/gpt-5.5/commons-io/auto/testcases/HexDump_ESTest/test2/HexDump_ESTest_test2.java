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
public class HexDump_ESTest_test2 extends HexDump_ESTest_scaffolding {

    private static final int DATA_LENGTH = 9;
    private static final long DISPLAY_OFFSET = 0L;
    private static final int START_INDEX = (int) (byte) 8;
    private static final int REQUESTED_LENGTH = (int) (byte) 101;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] data = new byte[DATA_LENGTH];
        PipedWriter output = new PipedWriter();

        try {
            HexDump.dump(data, DISPLAY_OFFSET, (Appendable) output, START_INDEX, REQUESTED_LENGTH);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Range [8, 8 + 101) out of bounds for length 9
            //
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
