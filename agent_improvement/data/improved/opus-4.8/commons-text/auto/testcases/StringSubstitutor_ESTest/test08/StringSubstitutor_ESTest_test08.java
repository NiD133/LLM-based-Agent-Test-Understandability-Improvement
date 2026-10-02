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
     * Verifies that {@link StringSubstitutor#setVariablePrefixMatcher(StringMatcher)}
     * rejects a {@code null} matcher by throwing an {@link IllegalArgumentException}.
     * The argument check is performed by Apache Commons Lang's {@code Validate} utility.
     */
    @Test(timeout = 4000)
    public void setVariablePrefixMatcherWithNullThrowsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        try {
            substitutor.setVariablePrefixMatcher((StringMatcher) null);
            fail("Expected an IllegalArgumentException because the prefix matcher must not be null");
        } catch (IllegalArgumentException e) {
            // Message: "Variable prefix matcher must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
