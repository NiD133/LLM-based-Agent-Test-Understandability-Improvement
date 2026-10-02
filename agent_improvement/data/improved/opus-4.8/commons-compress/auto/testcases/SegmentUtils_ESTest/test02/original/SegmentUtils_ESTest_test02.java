package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test02 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        long[][] longArray0 = new long[1][3];
        long[] longArray1 = new long[7];
        longArray1[3] = (-366L);
        longArray0[0] = longArray1;
        int int0 = SegmentUtils.countBit16(longArray0);
        assertEquals(1, int0);
    }
}
