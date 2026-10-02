package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test23 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#charSetMatcher(char...)} returns a matcher
     * instance when given a single-character array.
     */
    @Test(timeout = 4000)
    public void charSetMatcherWithSingleCharReturnsMatcher() throws Throwable {
        char[] singleCharSet = new char[1];

        StrMatcher matcher = StrMatcher.charSetMatcher(singleCharSet);

        assertNotNull(matcher);
    }
}
