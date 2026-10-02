package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test02 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteOrderMark#matches(int[])} returns {@code true}
     * when the supplied array equals the BOM's own bytes. Here the BOM is
     * built from an 8-element all-zero array, and the same array values are
     * passed to {@code matches}, so the byte sequences are identical.
     */
    @Test(timeout = 4000)
    public void matchesReturnsTrueForEqualByteSequence() throws Throwable {
        int[] bomBytes = new int[8];
        ByteOrderMark byteOrderMark = new ByteOrderMark("w-", bomBytes);

        boolean matches = byteOrderMark.matches(bomBytes);

        assertTrue(matches);
    }
}
