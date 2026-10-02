package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test21 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that StringMatcher.toString() returns a non-null representation
     * of the matcher built from the given string of characters.
     */
    @Test(timeout = 4000)
    public void toStringReturnsNonNullForStringMatcher() throws Throwable {
        StrMatcher.StringMatcher quoteMatcher = new StrMatcher.StringMatcher("'\"");

        String description = quoteMatcher.toString();

        assertNotNull(description);
    }
}
