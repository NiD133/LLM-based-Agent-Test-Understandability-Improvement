package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test17 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that the factory method {@link StrMatcher#tabMatcher()} returns
     * a usable (non-null) matcher instance for the tab character.
     */
    @Test(timeout = 4000)
    public void tabMatcherFactoryReturnsNonNullMatcher() throws Throwable {
        StrMatcher tabMatcher = StrMatcher.tabMatcher();

        assertNotNull(tabMatcher);
    }
}
