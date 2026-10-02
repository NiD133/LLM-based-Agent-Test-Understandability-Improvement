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
public class StrSubstitutor_ESTest_test22 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Tests that StrSubstitutor correctly replaces a variable within a substring range,
     * and that replaceIn on a buffer with no unresolved variables returns false.
     *
     * The mock StrMatcher drives the prefix/suffix detection:
     *   isMatch returns 0,0 (no match) then 1 (prefix found) then 5 (suffix found 5 chars later).
     * The mock StrLookup resolves any variable name to "fYxE".
     * replace("Q3N]D,Um> EQt", offset=1, length=5) processes the slice "3N]D," and
     * yields "3N" + "fYxE" = "3NfYxE" once the variable is substituted.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Arrange: a lookup that resolves every variable name to "fYxE"
        StrLookup<String> lookupReturningFyxe = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(lookupReturningFyxe).toString();
        doReturn("fYxE").when(lookupReturningFyxe).apply(anyString());

        // Arrange: a matcher used for both prefix and suffix detection.
        // isMatch returns 0 (no match), 0 (no match), 1 (prefix matched), 5 (suffix matched), 1694.
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 5, 1694)
                .when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        // Arrange: substitutor with custom escape char '1', using the same matcher for prefix and suffix
        char escapeChar = '1';
        StrSubstitutor substitutor = new StrSubstitutor(lookupReturningFyxe, prefixAndSuffixMatcher, prefixAndSuffixMatcher, escapeChar);

        // Act: replace within the substring at offset 1, length 5 of "Q3N]D,Um> EQt"
        // The processed slice is "3N]D," — the variable inside resolves to "fYxE"
        String result = substitutor.replace("Q3N]D,Um> EQt", 1, 5);

        // Assert: the variable region was substituted, producing "3N" + "fYxE"
        assertEquals("3NfYxE", result);

        // Act: attempt in-place replacement on a StringBuffer that contains no variable markers
        StringBuffer bufferWithNoVariables = new StringBuffer((CharSequence) "fYxE");
        boolean wasModified = substitutor.replaceIn(bufferWithNoVariables);

        // Assert: no substitution occurred in the plain-text buffer
        assertFalse(wasModified);

        // Assert: escape char is preserved as configured
        assertEquals('1', substitutor.getEscapeChar());
    }
}
