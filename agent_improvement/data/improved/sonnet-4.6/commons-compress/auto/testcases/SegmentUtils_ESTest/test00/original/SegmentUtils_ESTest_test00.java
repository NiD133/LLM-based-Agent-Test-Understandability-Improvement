package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test00 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        long[] longArray0 = new long[0];
        long[][] longArray1 = new long[5][6];
        longArray1[0] = longArray0;
        longArray1[1] = longArray0;
        longArray1[2] = longArray0;
        longArray1[3] = longArray0;
        longArray1[4] = longArray0;
        int int0 = SegmentUtils.countMatches(longArray1, (IMatcher) null);
        assertEquals(0, int0);
    }
}
