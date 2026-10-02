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
public class StringSubstitutor_ESTest_test17 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that {@link StringSubstitutor#replaceIn(StringBuilder)} returns
     * {@code false} (meaning "nothing was changed") when given a null target, and
     * that the custom escape character supplied to the constructor is retained.
     */
    @Test(timeout = 4000)
    public void replaceInNullBuilderMakesNoChangesAndKeepsEscapeChar() throws Throwable {
        final char customEscapeChar = '^';

        StringLookup variableResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn(null, null, null, null, null).when(variableResolver).toString();

        StringMatcher defaultSuffixMatcher = StringSubstitutor.DEFAULT_SUFFIX;

        // Reuse the default suffix matcher for prefix, suffix and value delimiter;
        // only the escape character is customised here.
        StringSubstitutor substitutor = new StringSubstitutor(
                variableResolver,
                defaultSuffixMatcher,
                defaultSuffixMatcher,
                customEscapeChar,
                defaultSuffixMatcher);

        boolean changed = substitutor.replaceIn((StringBuilder) null);

        assertFalse("Substituting into a null StringBuilder should report no change", changed);
        assertEquals("Constructor escape char should be preserved",
                customEscapeChar, substitutor.getEscapeChar());
    }
}
