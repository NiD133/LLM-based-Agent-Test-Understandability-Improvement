package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test23 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null source must be a no-op: it returns null without touching the
     * variable resolver or matchers, and leaves the configured escape character intact.
     */
    @Test(timeout = 4000)
    public void replaceNullSourceReturnsNullAndKeepsEscapeChar() throws Throwable {
        char escapeChar = '1';
        @SuppressWarnings("unchecked")
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher prefixSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());

        StrSubstitutor substitutor =
                new StrSubstitutor(variableResolver, prefixSuffixMatcher, prefixSuffixMatcher, escapeChar);

        String result = substitutor.replace((String) null, 1899, 1);

        assertNull(result);
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
