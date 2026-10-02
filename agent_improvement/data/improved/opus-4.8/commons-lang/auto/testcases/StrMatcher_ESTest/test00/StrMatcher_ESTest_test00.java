package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test00 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#stringMatcher(String)} returns a matcher
     * instance (never null) when given a non-empty string.
     */
    @Test(timeout = 4000)
    public void stringMatcherWithNonEmptyStringReturnsMatcher() throws Throwable {
        StrMatcher matcher = StrMatcher.stringMatcher("Zp_");

        assertNotNull(matcher);
    }
}
