package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test17 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtil#inSorted(String, String[])} returns false when the
     * needle is absent from the haystack. The haystack here has 9 slots (more than the
     * scan-vs-binary-search threshold of 8), so inSorted falls back to a binary search;
     * the two-space needle is not present, so the lookup reports false.
     */
    @Test(timeout = 4000)
    public void inSorted_returnsFalse_whenNeedleNotPresent() throws Throwable {
        String[] haystack = new String[9];
        haystack[0] = "rB h==e8f3nr";
        haystack[1] = "rB h==e8f3nr";
        haystack[4] = "rB h==e8f3nr";

        boolean found = StringUtil.inSorted("  ", haystack);

        assertFalse(found);
    }
}
