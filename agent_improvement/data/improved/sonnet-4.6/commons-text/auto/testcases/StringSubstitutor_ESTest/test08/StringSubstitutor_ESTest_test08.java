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
public class StringSubstitutor_ESTest_test08 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that passing a null StringMatcher as the variable prefix matcher
     * throws an IllegalArgumentException, since a null prefix matcher is invalid.
     */
    @Test(timeout = 4000)
    public void test08_setVariablePrefixMatcher_nullMatcher_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        try {
            substitutor.setVariablePrefixMatcher((StringMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
