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
public class StrSubstitutor_ESTest_test27 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replace() substitutes variables using the configured lookup and
     * prefix/suffix matchers, and that the escape character is preserved.
     *
     * <p>The mocked lookup always resolves any variable name to "fYxE". The mocked
     * prefix/suffix matcher reports variable-marker matches according to the scripted
     * isMatch() sequence (0, 0, 1, 1, 0), which drives the substitution so that the
     * input "fYxE" expands to "fYfYxE".</p>
     */
    @Test(timeout = 4000)
    public void replaceSubstitutesVariableAndKeepsEscapeChar() throws Throwable {
        final char escapeChar = '1';

        // Lookup that resolves every variable name to the fixed value "fYxE".
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(variableResolver).toString();
        doReturn("fYxE").when(variableResolver).apply(anyString());

        // Matcher used for both the variable prefix and suffix. The scripted isMatch()
        // results control where the substitutor detects variable markers in the input.
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null).when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 1, 0).when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        StrSubstitutor substitutor =
                new StrSubstitutor(variableResolver, prefixAndSuffixMatcher, prefixAndSuffixMatcher, escapeChar);

        String result = substitutor.replace("fYxE");

        assertEquals("fYfYxE", result);
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
