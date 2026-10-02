package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test01 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteOrderMark#matches(int[])} returns false when the
     * candidate array does not begin with this BOM's bytes.
     *
     * The UTF-32LE BOM is {0xFF, 0xFE, 0x00, 0x00}, but the candidate array is
     * filled with zeros, so its leading bytes do not match the BOM.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseWhenLeadingBytesDiffer() throws Throwable {
        ByteOrderMark utf32LeBom = ByteOrderMark.UTF_32LE;
        int[] allZeroBytes = new int[7];

        boolean matches = utf32LeBom.matches(allZeroBytes);

        assertFalse(matches);
    }
}
