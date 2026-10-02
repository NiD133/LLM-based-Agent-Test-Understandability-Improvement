package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test05 extends SegmentUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        int[] flags = new int[4];
        flags[2] = (-1622);

        int flagsWithBit16Set = SegmentUtils.countBit16(flags);

        assertEquals(1, flagsWithBit16Set);
    }
}
