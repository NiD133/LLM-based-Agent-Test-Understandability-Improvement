package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test18 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher#quoteMatcher()} returns a non-null matcher
     * that can match single or double quote characters.
     */
    @Test(timeout = 4000)
    public void test_quoteMatcher_returnsNonNullMatcher() throws Throwable {
        StrMatcher quoteMatcher = StrMatcher.quoteMatcher();
        assertNotNull(quoteMatcher);
    }
}
