package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test13 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that the {@code spaceMatcher()} factory method returns a usable,
     * non-null {@link StrMatcher} instance for matching the space character.
     */
    @Test(timeout = 4000)
    public void spaceMatcherReturnsNonNullInstance() throws Throwable {
        StrMatcher spaceMatcher = StrMatcher.spaceMatcher();

        assertNotNull(spaceMatcher);
    }
}
