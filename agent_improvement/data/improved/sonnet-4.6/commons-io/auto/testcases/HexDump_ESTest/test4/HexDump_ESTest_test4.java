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
     * Verifies that HexDump.dump throws ArrayIndexOutOfBoundsException when the
     * start index (127) is out of bounds for the given data array (length 17).
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // A 17-byte array; valid indices are 0–16
        byte[] data = new byte[17];

        // Index 127 is far beyond the end of the 17-element array, so dump() must reject it
        int outOfBoundsIndex = (int) (byte) 127;  // == 127
        long offset          = (long) (byte) 127; // == 127L
        int  length          = -3384;

        MockFile outputFile = new MockFile(
                "org.apache.commons.io.filefilter.CanExecuteFileFilter",
                "org.apache.commons.io.filefilter.CanExecuteFileFilter");
        MockFileWriter writer = new MockFileWriter(outputFile, true);

        try {
            HexDump.dump(data, offset, (Appendable) writer, outOfBoundsIndex, length);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected: "illegal index: 127 into array of length 17"
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
