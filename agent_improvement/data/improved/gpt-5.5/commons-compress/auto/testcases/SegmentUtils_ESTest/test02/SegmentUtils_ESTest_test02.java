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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        long[][] flagsBySegment = new long[1][3];
        long[] segmentFlags = new long[7];
        segmentFlags[3] = (-366L);
        flagsBySegment[0] = segmentFlags;

        int bit16Count = SegmentUtils.countBit16(flagsBySegment);

        assertEquals(1, bit16Count);
    }
}
