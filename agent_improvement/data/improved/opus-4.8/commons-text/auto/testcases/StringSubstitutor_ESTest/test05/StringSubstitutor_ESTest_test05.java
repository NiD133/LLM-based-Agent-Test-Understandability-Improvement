package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test05 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that {@link StringSubstitutor#replaceIn(StringBuilder)} performs no
     * substitution (returns {@code false}) when the source text contains no complete
     * variable expression, and that enabling escape preservation leaves the default
     * escape character ('$') unchanged.
     */
    @Test(timeout = 4000)
    public void replaceInWithoutCompleteVariableMakesNoSubstitution() throws Throwable {
        StringSubstitutor stringSubstitutor = new StringSubstitutor();
        stringSubstitutor.setPreserveEscapes(true);

        // Build a source that has stray prefix/suffix tokens but no full "${...}" variable:
        // "}" + four NUL chars + "$" + "${"
        char[] padding = new char[5];
        padding[4] = '$';
        StringBuilder source = new StringBuilder("}");
        source.append(padding);
        source.append((CharSequence) "${");

        boolean substitutionPerformed = stringSubstitutor.replaceIn(source);

        assertFalse("no complete variable, so nothing should be substituted", substitutionPerformed);
        assertEquals('$', stringSubstitutor.getEscapeChar());
    }
}
