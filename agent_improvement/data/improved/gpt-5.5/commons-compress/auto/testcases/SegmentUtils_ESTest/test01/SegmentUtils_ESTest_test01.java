package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test01 extends SegmentUtils_ESTest_scaffolding {

    private static final int NUMBER_OF_FLAGS = 8;
    private static final int MATCHING_FLAG_INDEX = 3;
    private static final long MATCHING_FLAG_VALUE = -1L;
    private static final int EXPECTED_MATCH_COUNT = 1;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        long[] flags = new long[NUMBER_OF_FLAGS];
        flags[MATCHING_FLAG_INDEX] = MATCHING_FLAG_VALUE;

        AttributeLayout matcher = new AttributeLayout("7.kMp'\u007fZ", 2, "> AQHR!W+eH?K(U", 2);

        int matchCount = SegmentUtils.countMatches(flags, (IMatcher) matcher);

        assertEquals(EXPECTED_MATCH_COUNT, matchCount);
    }
}
