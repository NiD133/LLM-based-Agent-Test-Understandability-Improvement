package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test10 extends StringUtil_ESTest_scaffolding {

    /**
     * When both the base and the relative URL use a scheme that Java has no
     * stream handler for (here the made-up "e:" scheme), neither can be parsed
     * into a {@link java.net.URL}. In that case {@code resolve} falls back to
     * returning the relative URL unchanged, because it still looks like a valid
     * URI scheme.
     */
    @Test(timeout = 4000)
    public void resolveWithUnsupportedSchemeReturnsRelativeUrlUnchanged() throws Throwable {
        String unsupportedSchemeUrl = "e:ONP";

        String resolved = StringUtil.resolve(unsupportedSchemeUrl, unsupportedSchemeUrl);

        assertEquals("e:ONP", resolved);
    }
}
