package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test01 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#charSetMatcher(String)} returns a non-null
     * matcher when given a single-character string.
     */
    @Test(timeout = 4000)
    public void charSetMatcherFromSingleCharStringReturnsMatcher() throws Throwable {
        StrMatcher matcher = StrMatcher.charSetMatcher("^");

        assertNotNull(matcher);
    }
}
