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

    // -366L has bit 16 set (0xFFFFFFFFFFFFFE92 & 0x10000 != 0)
    private static final long VALUE_WITH_BIT16_SET = -366L;
    private static final int EXPECTED_BIT16_COUNT = 1;

    @Test(timeout = 4000)
    public void test02_countBit16_onlyOneElementHasBit16Set() throws Throwable {
        // Build a 2D long array: one row of 7 elements, only index 3 has bit 16 set
        long[] rowFlags = new long[7];
        rowFlags[3] = VALUE_WITH_BIT16_SET;

        long[][] flagsMatrix = new long[1][3];
        flagsMatrix[0] = rowFlags;

        int bit16Count = SegmentUtils.countBit16(flagsMatrix);

        assertEquals(EXPECTED_BIT16_COUNT, bit16Count);
    }
}
