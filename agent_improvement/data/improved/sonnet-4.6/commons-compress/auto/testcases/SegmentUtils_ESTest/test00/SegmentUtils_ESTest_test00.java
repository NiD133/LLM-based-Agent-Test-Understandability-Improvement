package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test00 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that countMatches returns 0 when all inner arrays are empty.
     * The null matcher is never invoked because no elements exist to match against,
     * so no NullPointerException is thrown.
     */
    @Test(timeout = 4000)
    public void test_countMatches_allEmptyInnerArrays_returnsZero() throws Throwable {
        long[] emptyFlagsRow = new long[0];

        long[][] flagsGrid = new long[5][6];
        flagsGrid[0] = emptyFlagsRow;
        flagsGrid[1] = emptyFlagsRow;
        flagsGrid[2] = emptyFlagsRow;
        flagsGrid[3] = emptyFlagsRow;
        flagsGrid[4] = emptyFlagsRow;

        int matchCount = SegmentUtils.countMatches(flagsGrid, (IMatcher) null);

        assertEquals(0, matchCount);
    }
}
