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
     * padding(width, maxPaddingWidth) requires maxPaddingWidth to be >= -1
     * (where -1 means "unlimited"). A maxPaddingWidth of -2077 violates that
     * contract, so the Validate guard must reject it with an
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void padding_withMaxWidthBelowMinusOne_throwsIllegalArgument() throws Throwable {
        try {
            StringUtil.padding(0, -2077);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.isTrue(maxPaddingWidth >= -1) rejects the invalid limit
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
