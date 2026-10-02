package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test12 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // The shared matcher for double quotes should always be available.
        StrMatcher doubleQuoteMatcher = StrMatcher.doubleQuoteMatcher();

        assertNotNull(doubleQuoteMatcher);
    }
}
