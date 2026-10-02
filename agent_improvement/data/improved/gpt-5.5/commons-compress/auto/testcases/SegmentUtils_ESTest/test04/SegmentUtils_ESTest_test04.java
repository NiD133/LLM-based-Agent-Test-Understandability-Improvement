package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test04 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        long[] flagsWithoutBit16Set = new long[1];

        int bit16Count = SegmentUtils.countBit16(flagsWithoutBit16Set);

        assertEquals(0, bit16Count);
    }
}
