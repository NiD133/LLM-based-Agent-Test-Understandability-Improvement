package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test03 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        long[] longArray0 = new long[1];
        longArray0[0] = (long) (-4750);
        int int0 = SegmentUtils.countBit16(longArray0);
        assertEquals(1, int0);
    }
}
