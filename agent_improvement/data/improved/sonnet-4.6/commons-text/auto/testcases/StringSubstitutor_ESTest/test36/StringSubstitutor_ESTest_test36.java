package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test36 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null CharSequence with a custom escape character ('^')
     * does not throw an exception and that the escape character is retained after the call.
     *
     * The substitutor is constructed with the DEFAULT_SUFFIX matcher used for both
     * prefix and value-delimiter roles, and a mocked StringLookup that always returns null.
     */
    @Test(timeout = 4000)
    public void test_replaceNullCharSequence_retainsEscapeChar() throws Throwable {
        // Mock a StringLookup whose toString() always returns null
        StringLookup nullReturningLookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(nullReturningLookup).toString();

        // Use the built-in DEFAULT_SUFFIX matcher for prefix, suffix, and value-delimiter
        StringMatcher defaultSuffix = StringSubstitutor.DEFAULT_SUFFIX;
        char escapeChar = '^';

        StringSubstitutor substitutor = new StringSubstitutor(
                nullReturningLookup,
                defaultSuffix,   // prefix matcher
                defaultSuffix,   // suffix matcher
                escapeChar,
                defaultSuffix    // value-delimiter matcher
        );

        // Replacing null should complete without throwing
        substitutor.replace((CharSequence) null);

        // The escape character must still be '^' after the replace call
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
