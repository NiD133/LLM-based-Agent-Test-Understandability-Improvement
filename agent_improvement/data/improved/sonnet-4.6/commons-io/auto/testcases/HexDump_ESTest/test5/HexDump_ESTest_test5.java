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
     * Verifies that HexDump.dump throws ArrayIndexOutOfBoundsException
     * when a negative index (-1) is provided, which is outside the valid
     * range for the 9-element byte array.
     */
    @Test(timeout = 4000)
    public void test_dump_throwsArrayIndexOutOfBounds_whenIndexIsNegative() throws Throwable {
        byte[] data = new byte[9];
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            HexDump.dump(data, 0L, (OutputStream) output, -1);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected: "illegal index: -1 into array of length 9"
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
