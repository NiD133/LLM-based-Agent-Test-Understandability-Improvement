package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test2 extends HexDump_ESTest_scaffolding {

    /**
     * Verifies that dump() throws ArrayIndexOutOfBoundsException when the
     * requested range [index, index + length) exceeds the array bounds.
     *
     * Array length is 9, so starting at index 8 with length 101 would
     * require bytes up to index 109, which is out of bounds.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] data = new byte[9];
        // PipedWriter implements Appendable and serves as the output target
        PipedWriter output = new PipedWriter();

        int startIndex = 8;   // last valid index in a 9-element array
        int length = 101;     // far exceeds the remaining bytes (only 1 byte remains at index 8)

        try {
            HexDump.dump(data, 0L, (Appendable) output, startIndex, length);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Range [8, 8 + 101) out of bounds for length 9
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
