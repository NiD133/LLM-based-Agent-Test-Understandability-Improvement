package org.apache.commons.io;

import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.fail;

import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test2 extends HexDump_ESTest_scaffolding {

    /**
     * Dumping with a length that runs past the end of the array must fail:
     * starting at index 8 and asking for 101 bytes addresses the range
     * [8, 109), which lies outside the 9-element array.
     */
    @Test(timeout = 4000)
    public void dumpWithLengthBeyondArrayEndThrowsArrayIndexOutOfBounds() throws Throwable {
        byte[] data = new byte[9];
        PipedWriter output = new PipedWriter();
        long offset = 0L;
        int startIndex = 8;
        int length = 101;

        try {
            HexDump.dump(data, offset, (Appendable) output, startIndex, length);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Range [8, 8 + 101) out of bounds for length 9
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
