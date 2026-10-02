package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test08 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * The constructor StrSubstitutor(StrLookup, StrMatcher prefixMatcher,
     * StrMatcher suffixMatcher, char) requires a non-null variable prefix
     * matcher. Passing null as the prefix matcher must be rejected with an
     * IllegalArgumentException thrown by Apache Commons Lang's Validate.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNullVariablePrefixMatcher() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher suffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        StrMatcher nullPrefixMatcher = null;
        char escapeChar = '1';

        try {
            new StrSubstitutor(variableResolver, nullPrefixMatcher, suffixMatcher, escapeChar);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Variable prefix matcher must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
