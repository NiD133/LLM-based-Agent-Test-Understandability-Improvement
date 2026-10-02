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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        long[] emptyFlags = new long[0];
        long[][] flagGroups = new long[5][6];
        flagGroups[0] = emptyFlags;
        flagGroups[1] = emptyFlags;
        flagGroups[2] = emptyFlags;
        flagGroups[3] = emptyFlags;
        flagGroups[4] = emptyFlags;

        int matchCount = SegmentUtils.countMatches(flagGroups, (IMatcher) null);

        int expectedMatches = 0;
        assertEquals(expectedMatches, matchCount);
    }
}
