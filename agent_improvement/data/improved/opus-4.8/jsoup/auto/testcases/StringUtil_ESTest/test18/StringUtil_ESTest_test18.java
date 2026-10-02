package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test18 extends StringUtil_ESTest_scaffolding {

    /**
     * For arrays of length <= 8, inSorted falls back to a linear scan (rather than a
     * binary search). Here the needle is present in the haystack, so inSorted returns true.
     */
    @Test(timeout = 4000)
    public void inSortedReturnsTrueWhenNeedleIsPresent() throws Throwable {
        String needle = "JJJ~pnLT/[&fIq";

        // Haystack of length 7 (<= 8, so a linear scan is used); the needle sits at index 1,
        // the remaining slots are left null.
        String[] haystack = new String[7];
        haystack[1] = needle;

        boolean found = StringUtil.inSorted(needle, haystack);

        assertTrue(found);
    }
}
