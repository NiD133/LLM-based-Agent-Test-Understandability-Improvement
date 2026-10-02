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

    // -4750 in two's-complement (0xFFFFFFFFFFFFED72) has bit 16 set,
    // so countBit16 should report exactly one match in a single-element array.
    private static final long NEGATIVE_VALUE_WITH_BIT16_SET = -4750L;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        long[] flagsWithOneBit16Entry = { NEGATIVE_VALUE_WITH_BIT16_SET };

        int bit16Count = SegmentUtils.countBit16(flagsWithOneBit16Entry);

        assertEquals(1, bit16Count);
    }
}
