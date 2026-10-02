package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test57 extends StringUtil_ESTest_scaffolding {

    /**
     * When neither the base URL nor the relative URL is a valid, resolvable URL,
     * {@link StringUtil#resolve(String, String)} cannot produce an absolute URL
     * and therefore returns the empty string.
     */
    @Test(timeout = 4000)
    public void resolveReturnsEmptyStringForUnresolvableUrls() throws Throwable {
        String invalidBaseUrl = "??6i0w";
        String invalidRelativeUrl = "@Lfq^y0;lX=p%2";

        String resolved = StringUtil.resolve(invalidBaseUrl, invalidRelativeUrl);

        assertEquals("", resolved);
    }
}
