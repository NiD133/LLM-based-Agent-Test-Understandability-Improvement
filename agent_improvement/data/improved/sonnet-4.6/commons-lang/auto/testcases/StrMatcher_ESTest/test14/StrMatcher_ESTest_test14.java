package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test14 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#noneMatcher()} returns a non-null matcher instance.
     * The none matcher is a singleton that never matches any character.
     */
    @Test(timeout = 4000)
    public void test_noneMatcher_returnsNonNullInstance() throws Throwable {
        StrMatcher noneMatcher = StrMatcher.noneMatcher();
        assertNotNull(noneMatcher);
    }
}
