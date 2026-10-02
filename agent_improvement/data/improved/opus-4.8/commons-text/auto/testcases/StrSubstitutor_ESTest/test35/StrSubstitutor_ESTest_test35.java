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
public class StrSubstitutor_ESTest_test35 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer) reports "no substitution made" (returns false)
     * when the prefix/suffix matchers never recognise a complete variable in the buffer,
     * and that the configured escape character is preserved on the substitutor.
     */
    @Test(timeout = 4000)
    public void replaceInReportsNoChangeWhenNoVariableMatched() throws Throwable {
        // A variable resolver that is never actually consulted (no variable is matched).
        @SuppressWarnings("unchecked")
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());

        // A matcher used as both the prefix and suffix matcher. Its isMatch(...) stub returns
        // 0 (no match) for most probes, so a full variable is never delimited.
        StrMatcher prefixSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(prefixSuffixMatcher).toString();
        doReturn(0, 0, 1, 0).when(prefixSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        char escapeChar = '1';
        StrSubstitutor substitutor =
                new StrSubstitutor(variableResolver, prefixSuffixMatcher, prefixSuffixMatcher, escapeChar);

        StringBuffer buffer = new StringBuffer((CharSequence) "fYxE");

        boolean substitutionMade = substitutor.replaceIn(buffer);

        assertFalse("No variable should be substituted in the buffer", substitutionMade);
        assertEquals('1', substitutor.getEscapeChar());
    }
}
