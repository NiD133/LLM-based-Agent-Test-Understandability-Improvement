package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test16 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that inSorted finds an empty string when the array has 9 elements
     * (triggering the binary-search code path instead of the linear scan used for
     * arrays of length <= 8), and the empty string is placed at index 4 — the
     * midpoint that binary search probes first — so the search succeeds even
     * though all other array slots remain null.
     */
    @Test(timeout = 4000)
    public void test_inSorted_binarySearchPath_findsEmptyStringAtMidpoint() throws Throwable {
        // 9-element array exceeds the threshold of 8, so inSorted will use Arrays.binarySearch
        String[] haystack = new String[9];
        // Place the needle at index 4 (the binary-search midpoint for a 9-element array)
        // so it is found on the very first probe without comparing against any null slot
        haystack[4] = "";

        boolean found = StringUtil.inSorted("", haystack);

        assertTrue(found);
    }
}
