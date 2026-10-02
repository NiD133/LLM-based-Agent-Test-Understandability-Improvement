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
public class HexDump_ESTest_test3 extends HexDump_ESTest_scaffolding {

    /**
     * A negative {@code length} is rejected by {@link HexDump#dump(byte[], long, Appendable, int, int)}
     * with an {@link ArrayIndexOutOfBoundsException}, because the requested range
     * {@code [index, index + length)} falls outside the bounds of the data array.
     */
    @Test(timeout = 4000)
    public void dumpWithNegativeLengthThrowsArrayIndexOutOfBounds() throws Throwable {
        byte[] data = new byte[9];
        int startIndex = 0;
        int negativeLength = -186;
        long offset = 1L;
        PipedWriter appendable = new PipedWriter();

        try {
            HexDump.dump(data, offset, (Appendable) appendable, startIndex, negativeLength);
            fail("Expected an ArrayIndexOutOfBoundsException for a negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Message: "Range [0, 0 + -186) out of bounds for length 9"
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
