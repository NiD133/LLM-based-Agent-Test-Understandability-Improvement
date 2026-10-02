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

    /**
     * Verifies that countBit16(long[]) returns 0 when no flag in the array has
     * bit 16 set. A single-element array left at its default value (0) has no
     * bits set, so the expected count is 0.
     */
    @Test(timeout = 4000)
    public void testCountBit16ReturnsZeroWhenNoFlagHasBit16Set() throws Throwable {
        long[] flagsWithoutBit16 = new long[1];

        int bit16Count = SegmentUtils.countBit16(flagsWithoutBit16);

        assertEquals(0, bit16Count);
    }
}
