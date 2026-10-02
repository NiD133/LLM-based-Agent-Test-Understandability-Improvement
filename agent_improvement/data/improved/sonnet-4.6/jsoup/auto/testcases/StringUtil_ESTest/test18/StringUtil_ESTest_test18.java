package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test18 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // A 7-element array triggers the linear scan path in inSorted (length <= 8)
        String needle = "JJJ~pnLT/[&fIq";
        String[] haystack = new String[7];
        haystack[1] = needle;

        boolean found = StringUtil.inSorted(needle, haystack);

        assertTrue(found);
    }
}
