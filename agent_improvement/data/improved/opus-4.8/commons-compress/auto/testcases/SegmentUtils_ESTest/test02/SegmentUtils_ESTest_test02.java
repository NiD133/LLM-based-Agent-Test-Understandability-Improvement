package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test02 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link SegmentUtils#countBit16(long[][])} counts each element
     * whose bit 16 (the 0x10000 mask) is set. A single negative value (-366) has
     * all high-order bits set in two's complement, so bit 16 is set and the count is 1.
     */
    @Test(timeout = 4000)
    public void countBit16OnNestedArrayCountsValuesWithBit16Set() throws Throwable {
        long[] flagsRow = new long[7];
        flagsRow[3] = -366L;
        long[][] flags = new long[1][];
        flags[0] = flagsRow;

        int bit16Count = SegmentUtils.countBit16(flags);

        assertEquals(1, bit16Count);
    }
}
