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
public class StrSubstitutor_ESTest_test35 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer) returns false when no variable substitution
     * occurs in the buffer, and that the configured escape character is preserved.
     *
     * The prefix/suffix matcher is stubbed so that it never successfully identifies
     * a complete variable expression in "fYxE": isMatch returns 0 (no match) for the
     * first two prefix-check calls, 1 (match) for the third, and 0 for the fourth,
     * meaning no full prefix+suffix pair is found and therefore no substitution is made.
     */
    @Test(timeout = 4000)
    public void test35() throws Throwable {
        // Set up a no-op variable lookup
        StrLookup<String> noOpLookup = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());

        // Set up a matcher that never finds a complete variable delimiter pair in the input
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 0).when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        char escapeChar = '1';
        StrSubstitutor substitutor = new StrSubstitutor(noOpLookup, prefixAndSuffixMatcher, prefixAndSuffixMatcher, escapeChar);

        StringBuffer inputBuffer = new StringBuffer((CharSequence) "fYxE");

        // No variable delimiters are matched, so no substitution should occur
        boolean substitutionOccurred = substitutor.replaceIn(inputBuffer);

        assertFalse("replaceIn should return false when no substitution was performed", substitutionOccurred);
        assertEquals("Escape character should remain as configured", escapeChar, substitutor.getEscapeChar());
    }
}
