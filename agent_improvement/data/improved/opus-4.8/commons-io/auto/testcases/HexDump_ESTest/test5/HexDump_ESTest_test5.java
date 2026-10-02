package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test5 extends HexDump_ESTest_scaffolding {

    /**
     * A negative start index is rejected by {@link HexDump#dump(byte[], long, OutputStream, int)}.
     * Here the index is -1, which is below the array's lower bound, so the method
     * must throw an ArrayIndexOutOfBoundsException ("illegal index: -1 into array of length 9").
     */
    @Test(timeout = 4000)
    public void dumpWithNegativeIndexThrowsArrayIndexOutOfBounds() throws Throwable {
        byte[] data = new byte[9];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int negativeIndex = -1;
        long offset = 0L;

        try {
            HexDump.dump(data, offset, (OutputStream) output, negativeIndex);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // illegal index: -1 into array of length 9
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
