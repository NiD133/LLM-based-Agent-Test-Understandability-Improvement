package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test08 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that constructing a StrSubstitutor with a null prefix matcher
     * throws IllegalArgumentException with an appropriate message.
     *
     * The constructor signature used is:
     *   StrSubstitutor(StrLookup variableResolver, StrMatcher prefixMatcher,
     *                  StrMatcher suffixMatcher, char escapeChar)
     *
     * Passing null for prefixMatcher should be rejected immediately by the
     * constructor's parameter validation (via Validate.notNull in commons-lang3).
     */
    @Test(timeout = 4000)
    public void test08_constructorRejectsNullPrefixMatcher() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher suffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        StrMatcher nullPrefixMatcher = null;
        char escapeChar = '1';

        try {
            new StrSubstitutor(variableResolver, nullPrefixMatcher, suffixMatcher, escapeChar);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Variable prefix matcher must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
