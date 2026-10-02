package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test11 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that when the variable prefix and suffix are identical, no
     * substitution happens (so replaceIn reports "no change") and the escape
     * character is left at its default value of '$'.
     */
    @Test(timeout = 4000)
    public void replaceInWithIdenticalPrefixAndSuffixMakesNoChange() throws Throwable {
        // Use the same marker for both the prefix and the suffix.
        String prefixAndSuffix = ".@let_E6[gcD#*{";

        StrSubstitutor substitutor = new StrSubstitutor();
        // setVariableSuffix returns the same instance, so both settings apply to "substitutor".
        substitutor.setVariableSuffix(prefixAndSuffix);
        substitutor.setVariablePrefix(prefixAndSuffix);

        // The text contains the marker but never forms a complete variable reference.
        StringBuilder text = new StringBuilder(prefixAndSuffix);
        text.append(prefixAndSuffix);

        boolean changed = substitutor.replaceIn(text);

        assertFalse(changed);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
