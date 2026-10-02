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

    /**
     * Verifies that {@link SegmentUtils#countMatches(long[], IMatcher)} returns the
     * number of array elements accepted by the given matcher.
     *
     * The flags array contains a single non-zero entry (all bits set), and the
     * remaining entries are zero. The matcher therefore accepts exactly one element,
     * so the expected count is 1.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Flags array where only one element carries any set bits.
        long[] flags = new long[8];
        flags[3] = -1L; // all bits set, so this element will match

        // AttributeLayout implements IMatcher and is used here as the matcher.
        AttributeLayout matcher = new AttributeLayout("7.kMp'Z", 2, "> AQHR!W+eH?K(U", 2);

        int matchCount = SegmentUtils.countMatches(flags, (IMatcher) matcher);

        assertEquals(1, matchCount);
    }
}
