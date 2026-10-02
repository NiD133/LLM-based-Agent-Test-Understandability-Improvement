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
     * Verifies that matches() returns true when the BOM's byte sequence equals
     * the bytes at the start of the test array (both are the same 8-zero-element array).
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        int[] bomBytes = new int[8];
        ByteOrderMark bom = new ByteOrderMark("w-", bomBytes);
        boolean matches = bom.matches(bomBytes);
        assertTrue(matches);
    }
}
