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
        // A single-element array initialized to zero has no bit-16 flags set,
        // so countBit16 should return 0.
        long[] flagsWithNoBit16Set = new long[1];
        int count = SegmentUtils.countBit16(flagsWithNoBit16Set);
        assertEquals(0, count);
    }
}
