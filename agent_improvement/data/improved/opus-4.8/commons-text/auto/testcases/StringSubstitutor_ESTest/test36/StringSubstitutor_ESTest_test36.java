package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
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
     * Verifies that constructing a StringSubstitutor with a custom escape character
     * keeps that character available via getEscapeChar(), and that replacing a null
     * input is handled gracefully.
     */
    @Test(timeout = 4000)
    public void replaceNullInputPreservesCustomEscapeChar() throws Throwable {
        final char customEscapeChar = '^';

        // A mock lookup is enough here: replacing a null input never triggers any lookup.
        StringLookup variableResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(variableResolver).toString();

        StringMatcher defaultSuffixMatcher = StringSubstitutor.DEFAULT_SUFFIX;

        StringSubstitutor substitutor = new StringSubstitutor(
                variableResolver,
                defaultSuffixMatcher,   // prefix matcher
                defaultSuffixMatcher,   // suffix matcher
                customEscapeChar,
                defaultSuffixMatcher);  // value delimiter matcher

        // Replacing a null CharSequence returns null and must not alter configuration.
        substitutor.replace((CharSequence) null);

        assertEquals(customEscapeChar, substitutor.getEscapeChar());
    }
}
