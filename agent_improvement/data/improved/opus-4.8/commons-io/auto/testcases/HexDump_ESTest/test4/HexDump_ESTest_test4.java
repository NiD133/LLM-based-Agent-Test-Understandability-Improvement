package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test4 extends HexDump_ESTest_scaffolding {

    /**
     * When the start index passed to {@link HexDump#dump(byte[], long, Appendable, int, int)}
     * is greater than or equal to the data array's length, the method must reject it
     * with an {@link ArrayIndexOutOfBoundsException} before writing anything.
     *
     * Here the array holds 17 bytes but the index is 127, so the call is expected to fail.
     */
    @Test(timeout = 4000)
    public void dumpWithIndexBeyondArrayLengthThrowsArrayIndexOutOfBounds() throws Throwable {
        byte[] data = new byte[17];
        int outOfBoundsIndex = 127;
        long offset = 127L;
        int length = -3384;

        MockFile targetFile = new MockFile(
                "org.apache.commons.io.filefilter.CanExecuteFileFilter",
                "org.apache.commons.io.filefilter.CanExecuteFileFilter");
        MockFileWriter appendable = new MockFileWriter(targetFile, true);

        try {
            HexDump.dump(data, offset, (Appendable) appendable, outOfBoundsIndex, length);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // illegal index: 127 into array of length 17
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
