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
public class StrSubstitutor_ESTest_test06 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that constructing a StrSubstitutor with a null suffix matcher
     * throws an IllegalArgumentException, since the suffix matcher is required
     * to identify the end of variable references (e.g., the closing '}' in '${var}').
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher prefixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        StrMatcher nullSuffixMatcher = null;
        char escapeChar = 'x';
        StrMatcher valueDelimiterMatcher = null;

        try {
            new StrSubstitutor(variableResolver, prefixMatcher, nullSuffixMatcher, escapeChar, valueDelimiterMatcher);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The constructor must reject a null suffix matcher with a clear error message
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
