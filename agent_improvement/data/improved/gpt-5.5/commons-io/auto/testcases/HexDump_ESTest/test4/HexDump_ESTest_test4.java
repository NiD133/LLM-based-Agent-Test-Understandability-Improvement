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
public class HexDump_ESTest_test4 extends HexDump_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        final int dataLength = 17;
        final long displayOffset = (long) (byte) 127;
        final int outOfBoundsIndex = (int) (byte) 127;
        final int requestedLength = -3384;
        final String virtualFileName = "org.apache.commons.io.filefilter.CanExecuteFileFilter";

        byte[] data = new byte[dataLength];
        MockFile virtualFile = new MockFile(virtualFileName, virtualFileName);
        MockFileWriter appendable = new MockFileWriter(virtualFile, true);

        try {
            HexDump.dump(data, displayOffset, (Appendable) appendable, outOfBoundsIndex, requestedLength);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // illegal index: 127 into array of length 17
            //
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
