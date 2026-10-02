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

    // Verifies that countMatches returns 1 when exactly one element in the flags array
    // matches the given AttributeLayout matcher (the non-zero flag at index 3).
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Set up an array of 8 flags, all zero except index 3 which is -1L (all bits set)
        long[] flags = new long[8];
        flags[3] = -1L;

        // AttributeLayout implements IMatcher; its match logic depends on its layout string
        AttributeLayout matcher = new AttributeLayout("7.kMp'Z", 2, "> AQHR!W+eH?K(U", 2);

        // Only the flag at index 3 (-1L) satisfies the matcher, so the count should be 1
        int matchCount = SegmentUtils.countMatches(flags, (IMatcher) matcher);
        assertEquals(1, matchCount);
    }
}
