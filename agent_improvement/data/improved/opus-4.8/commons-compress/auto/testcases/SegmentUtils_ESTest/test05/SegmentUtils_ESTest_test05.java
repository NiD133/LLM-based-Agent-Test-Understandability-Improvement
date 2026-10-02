package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test05 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countBit16(int[]) should count how many array entries have bit 16
     * (the 0x10000 mask) set. The value -1622 is negative, so in two's
     * complement all its high-order bits (including bit 16) are set, while
     * the three remaining entries are 0. Hence exactly one entry matches.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        int[] flags = new int[4];
        flags[2] = -1622;

        int bit16Count = SegmentUtils.countBit16(flags);

        assertEquals(1, bit16Count);
    }
}
