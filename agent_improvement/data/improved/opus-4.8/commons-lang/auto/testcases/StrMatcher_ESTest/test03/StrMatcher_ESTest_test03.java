package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test03 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#charSetMatcher(char...)} returns a non-null
     * matcher even when the character array is null. A null array means
     * "match nothing", so the factory falls back to the shared none-matcher
     * rather than returning null.
     */
    @Test(timeout = 4000)
    public void charSetMatcherWithNullCharArrayReturnsNonNullMatcher() throws Throwable {
        char[] nullChars = null;

        StrMatcher matcher = StrMatcher.charSetMatcher(nullChars);

        assertNotNull(matcher);
    }
}
