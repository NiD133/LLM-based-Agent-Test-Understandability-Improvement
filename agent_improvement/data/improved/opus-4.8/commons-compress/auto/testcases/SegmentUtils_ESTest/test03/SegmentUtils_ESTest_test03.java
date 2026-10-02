package org.apache.commons.compress.harmony.unpack200;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test03 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countBit16(long[]) counts how many flags have bit 16 (the 0x10000 mask) set.
     * The single flag -4750 has bit 16 set, so the expected count is 1.
     */
    @Test(timeout = 4000)
    public void testCountBit16CountsFlagWithBit16Set() throws Throwable {
        long[] flags = { -4750L };

        int bit16Count = SegmentUtils.countBit16(flags);

        assertEquals(1, bit16Count);
    }
}
