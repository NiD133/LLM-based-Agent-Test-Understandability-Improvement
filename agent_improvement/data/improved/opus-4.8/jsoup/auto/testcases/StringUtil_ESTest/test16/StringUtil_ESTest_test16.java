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
     * Verifies that inSorted finds an empty-string needle that is present in the haystack.
     * The haystack has 9 slots (more than the length-8 linear-scan threshold, so a binary
     * search is used); only one slot holds the empty string and the rest are null.
     */
    @Test(timeout = 4000)
    public void inSorted_findsEmptyStringPresentInHaystack() throws Throwable {
        String[] haystack = new String[9];
        haystack[4] = "";

        boolean found = StringUtil.inSorted("", haystack);

        assertTrue(found);
    }
}
