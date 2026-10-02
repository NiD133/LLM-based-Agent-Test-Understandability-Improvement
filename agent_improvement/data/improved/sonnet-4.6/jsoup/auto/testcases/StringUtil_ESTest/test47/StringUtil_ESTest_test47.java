package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test47 extends StringUtil_ESTest_scaffolding {

    /**
     * maxPaddingWidth must be >= -1; passing a value below that lower bound should
     * cause Validate.isTrue to throw IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test47() throws Throwable {
        int validWidth = 0;
        int invalidMaxPaddingWidth = -2077; // below the minimum allowed value of -1

        try {
            StringUtil.padding(validWidth, invalidMaxPaddingWidth);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
