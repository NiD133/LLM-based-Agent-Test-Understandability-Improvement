package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test19 extends StringUtil_ESTest_scaffolding {

    /**
     * inSorted should return false when the needle is absent from the haystack.
     * Here the haystack holds 7 null entries (small enough for the linear scan
     * branch), so no element can match the needle.
     */
    @Test(timeout = 4000)
    public void inSortedReturnsFalseWhenNeedleNotPresent() throws Throwable {
        String needle = "JJJ~pnLT/[&fIq";
        String[] haystack = new String[7];

        boolean found = StringUtil.inSorted(needle, haystack);

        assertFalse(found);
    }
}
