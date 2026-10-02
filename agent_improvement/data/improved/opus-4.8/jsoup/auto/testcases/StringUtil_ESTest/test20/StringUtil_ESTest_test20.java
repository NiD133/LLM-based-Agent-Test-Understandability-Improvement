package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test20 extends StringUtil_ESTest_scaffolding {

    /**
     * StringUtil.in returns true when the needle is present in the haystack.
     * Here the empty string "" sits in the first slot of the array, so searching
     * for "" finds it on the first comparison.
     */
    @Test(timeout = 4000)
    public void inFindsEmptyStringAtFirstPosition() throws Throwable {
        String[] haystack = new String[5];
        haystack[0] = "";

        boolean found = StringUtil.in("", haystack);

        assertTrue("Empty string should be found in the haystack", found);
    }
}
