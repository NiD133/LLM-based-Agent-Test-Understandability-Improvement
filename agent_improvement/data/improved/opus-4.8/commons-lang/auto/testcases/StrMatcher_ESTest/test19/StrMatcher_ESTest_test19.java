package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test19 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that the comma matcher factory returns a usable (non-null)
     * StrMatcher instance.
     */
    @Test(timeout = 4000)
    public void commaMatcherReturnsNonNullInstance() throws Throwable {
        StrMatcher commaMatcher = StrMatcher.commaMatcher();

        assertNotNull(commaMatcher);
    }
}
