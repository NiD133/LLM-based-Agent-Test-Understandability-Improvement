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
    public void test_dump_throwsArrayIndexOutOfBoundsException_whenLengthIsNegative() throws Throwable {
        byte[] nineZeroBytes = new byte[9];
        PipedWriter appendableOutput = new PipedWriter();
        // A negative length is an invalid range; HexDump must reject it with ArrayIndexOutOfBoundsException.
        try {
            HexDump.dump(nineZeroBytes, 1L, (Appendable) appendableOutput, 0, (-186));
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Range [0, 0 + -186) out of bounds for length 9
            //
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
