package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test13 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_spaceMatcherReturnsNonNullInstance() throws Throwable {
        // StrMatcher.spaceMatcher() should return the singleton space-character matcher, never null
        StrMatcher spaceMatcher = StrMatcher.spaceMatcher();
        assertNotNull(spaceMatcher);
    }
}
