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
public class StrSubstitutor_ESTest_test22 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Drives StrSubstitutor with stubbed collaborators so its prefix/suffix scanning
     * follows a fixed, scripted path. The variable resolver always returns "fYxE", and
     * the matcher reports matches according to a canned sequence of return values. This
     * lets us assert the exact substituted result and confirm a second replace finds
     * nothing to change.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Variable resolver: every variable name resolves to the literal "fYxE".
        @SuppressWarnings("unchecked")
        StrLookup<String> variableResolver =
                (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(variableResolver).toString();
        doReturn("fYxE").when(variableResolver).apply(anyString());

        // Matcher used for both prefix and suffix. isMatch returns a scripted sequence of
        // match lengths (0 = no match) that steers the substitutor through the source text.
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 5, 1694)
                .when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        // Use the same matcher for prefix and suffix, with '1' as the escape character.
        StrSubstitutor substitutor =
                new StrSubstitutor(variableResolver, prefixAndSuffixMatcher, prefixAndSuffixMatcher, '1');

        // Replace within the 5-char window starting at offset 1 of the source string.
        String replaced = substitutor.replace("Q3N]D,Um> EQt", 1, 5);
        assertEquals("3NfYxE", replaced);

        // Re-running over the resolver's own output yields no substitution this time.
        StringBuffer buffer = new StringBuffer((CharSequence) "fYxE");
        boolean changed = substitutor.replaceIn(buffer);
        assertFalse(changed);

        // The escape character supplied to the constructor is preserved.
        assertEquals('1', substitutor.getEscapeChar());
    }
}
