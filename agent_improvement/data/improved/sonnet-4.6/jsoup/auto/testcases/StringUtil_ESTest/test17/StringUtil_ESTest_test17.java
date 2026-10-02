package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test17 extends StringUtil_ESTest_scaffolding {

    // inSorted uses binary search when the array length exceeds 8.
    // With 9 slots and only a few set to the same non-matching value,
    // searching for "  " (two spaces) must return false.
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        String[] haystack = new String[9];
        haystack[0] = "rB h==e8f3nr";
        haystack[1] = "rB h==e8f3nr";
        haystack[4] = "rB h==e8f3nr";

        boolean found = StringUtil.inSorted("  ", haystack);

        assertFalse(found);
    }
}
