package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test06 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that setting a null variable suffix matcher is rejected with an
     * IllegalArgumentException thrown by the Apache Commons Lang Validate check.
     */
    @Test(timeout = 4000)
    public void setVariableSuffixMatcher_withNull_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor stringSubstitutor = new StringSubstitutor();

        try {
            stringSubstitutor.setVariableSuffixMatcher((StringMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Variable suffix matcher must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
